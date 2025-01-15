package com.example.woltapplication.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Sections(

//  @SerialName("content_id"         ) var contentId        : String?          = null,
//  @SerialName("hide_delivery_info" ) var hideDeliveryInfo : Boolean?         = null,
    @SerialName("items") var items: ArrayList<Items> = arrayListOf(),
//  @SerialName("name"               ) var name             : String?          = null,
//  @SerialName("template"           ) var template         : String?          = null,
//  @SerialName("title"              ) var title            : String?          = null

)