package com.tvacstudio.motionlayout.config

import com.tvacstudio.motionlayout.api.Endpoint

object RemoteConfig {

    val baseUrl = Endpoint.PRODUCTION.url

    object ConnectionConstants {

        const val CONNECTION_TIME_OUT_IN_SECONDS: Long = 15
        const val READ_TIME_OUT_IN_SECONDS: Long = 15
        const val WRITE_TIME_OUT_IN_SECONDS: Long = 15

    }

}