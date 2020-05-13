package com.tvacstudio.motionlayout.api.services

import com.tvacstudio.motionlayout.api.response.FitnessInfo
import com.tvacstudio.motionlayout.api.response.FitnessMembers
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FitnessApi {

    @GET("info.json")
    fun getFitnessInfo(): Single<FitnessInfo>

    @GET("members/{page}.json")
    fun getFitnessMembers(@Path("page") page: Int): Single<FitnessMembers>

}