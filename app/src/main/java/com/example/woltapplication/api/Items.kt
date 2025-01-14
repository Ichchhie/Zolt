package com.example.woltapplication.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Items(

//  @SerialName("filtering"              ) var filtering            : Filtering?        = Filtering(),
    @SerialName("image") var image: Image? = Image(),
//  @SerialName("link"                   ) var link                 : Link?             = Link(),
//  @SerialName("sorting"                ) var sorting              : Sorting?          = Sorting(),
//  @SerialName("telemetry_object_id"    ) var telemetryObjectId    : String?           = null,
//  @SerialName("telemetry_venue_badges" ) var telemetryVenueBadges : ArrayList<String> = arrayListOf(),
//  @SerialName("template"               ) var template             : String?           = null,
//  @SerialName("title"                  ) var title                : String?           = null,
//  @SerialName("track_id"               ) var trackId              : String?           = null,
    @SerialName("venue") var venue: Venue? = Venue()

)