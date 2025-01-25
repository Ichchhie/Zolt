package com.example.woltapplication.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class RestaurantData(
    @SerialName("sections") var sections: ArrayList<Sections> = arrayListOf(),
)
