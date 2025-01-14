package com.example.woltapplication.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Image(

    @SerialName("blurhash") var blurhash: String? = null,
    @SerialName("url") var url: String? = null

)