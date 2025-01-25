package com.example.woltapplication.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Sections(
    @SerialName("items") var items: ArrayList<Items> = arrayListOf(),
)