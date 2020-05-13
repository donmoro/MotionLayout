package com.tvacstudio.motionlayout.activities.main

import com.tvacstudio.motionlayout.api.NetworkModule
import com.tvacstudio.motionlayout.api.response.FitnessInfo
import com.tvacstudio.motionlayout.api.response.FitnessMembers
import com.tvacstudio.motionlayout.api.response.Person
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers

// ToDo repository...
class MainActivityPresenter(private val view: View) {

    private val compositeDisposable = CompositeDisposable()

    data class ListObject(val fitnessMembers: FitnessMembers? = null, val me: Person? = null)

    // ToDo data loading კარგი იქნებოდა...
    interface View {
        fun fitnessProgress(isLoading: Boolean)
        fun updateFitnessInfo(fitnessInfo: FitnessInfo?)
        fun userListProgress(isLoading: Boolean)
        fun updateUserList(members: FitnessMembers?, me: Person? = null)
    }

    // ToDo საჭიროა Refactor paging - ისთვის (გასატესტია!)
    // პარამეტრი დასამატებელია!
    fun loadData() {

        val fitnessInfo = NetworkModule.fitnessApi.getFitnessInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.fitnessProgress(true) }
            .doOnSuccess { view.updateFitnessInfo(it) }
            .doOnError { }
            .doFinally { view.fitnessProgress(false) }
            .toObservable()
            .flatMap { t -> Observable.just(t?.me) }

        val personList = NetworkModule.fitnessApi.getFitnessMembers(1) // ToDo
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.userListProgress(true) }
            .doOnSuccess { view.updateUserList(it) }
            .doOnError { }
            .doFinally { view.userListProgress(false) }
            .toObservable()

        val disposable = Observable.combineLatest(
            fitnessInfo,
            personList,
            BiFunction<Person?, FitnessMembers, ListObject> { t1, t2 -> ListObject(t2, t1) }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.userListProgress(true) }
            .doOnError { }
            .doFinally { view.userListProgress(false) }
            .subscribe { view.updateUserList(it.fitnessMembers, it.me) }

        compositeDisposable.add(disposable)

    }

    fun disposeAll() {
        compositeDisposable.dispose()
    }

}