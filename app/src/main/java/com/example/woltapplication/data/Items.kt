package com.example.woltapplication.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Items(

    @SerialName("image") var image: Image? = Image(),
    @SerialName("venue") var venue: Venue? = Venue()

)