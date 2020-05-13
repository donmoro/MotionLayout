package com.tvacstudio.motionlayout.api.response

data class FitnessInfo(
    val name: String?,
    val imageUrl: String?,
    val info: List<ClubInfo>?,
    val me: Person
)

data class ClubInfo(
    val key: String?,
    val value: String?
)

data class Person(
    val id: Long?,
    val position: Int?,
    val name: String?,
    val imageUrl: String?,
    val hours: String?
)