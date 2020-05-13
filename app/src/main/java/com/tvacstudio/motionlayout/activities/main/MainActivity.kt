package com.tvacstudio.motionlayout.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.tvacstudio.motionlayout.R
import com.tvacstudio.motionlayout.adapters.MembersAdapter
import com.tvacstudio.motionlayout.api.response.FitnessInfo
import com.tvacstudio.motionlayout.api.response.FitnessMembers
import com.tvacstudio.motionlayout.api.response.Person
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_user_info.*

class MainActivity : AppCompatActivity(),
    MainActivityPresenter.View {

    private lateinit var mainActivityPresenter: MainActivityPresenter

    private var membersAdapter: MembersAdapter = MembersAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.init()

        mainActivityPresenter =
            MainActivityPresenter(
                this
            )

        mainActivityPresenter.loadData()

    }

    private fun init() {
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.VERTICAL
        membersList.layoutManager = lm
        membersList.adapter = membersAdapter
        membersAdapter.notifyDataSetChanged()
    }

    override fun fitnessProgress(isLoading: Boolean) {
        if (isLoading) {
            headerProgressBar.visibility = View.VISIBLE
            headerContent.visibility = View.GONE
        } else {
            headerProgressBar.visibility = View.GONE
            headerContent.visibility = View.VISIBLE
        }
    }

    override fun updateFitnessInfo(fitnessInfo: FitnessInfo?) {
        participant.text = fitnessInfo?.info?.get(0)?.value ?: ""
        averageTime.text = fitnessInfo?.info?.get(1)?.value ?: ""
        totalTime.text = fitnessInfo?.info?.get(2)?.value ?: ""
    }

    override fun userListProgress(isLoading: Boolean) {
        if (isLoading) {
            userListLoading.visibility = View.VISIBLE
            membersList.visibility = View.GONE
        } else {
            userListLoading.visibility = View.GONE
            membersList.visibility = View.VISIBLE
        }
    }

    // ToDo წინა page - ებიდან დატა უნდა დაქეშირდეს [მოსაფიქრებელი]
    override fun updateUserList(members: FitnessMembers?, me: Person?) {
        membersAdapter.updateUserList(members?.members!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityPresenter.disposeAll()
    }

}