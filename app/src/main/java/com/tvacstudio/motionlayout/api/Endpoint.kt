package com.tvacstudio.motionlayout.api

enum class Endpoint {

    PRODUCTION {
        override val url: String
            get() = "https://nfa.leavingstone.club/"
    };

    abstract val url: String

}