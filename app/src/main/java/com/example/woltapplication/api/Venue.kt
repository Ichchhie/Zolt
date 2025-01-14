package com.example.woltapplication.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Venue(

//  @SerialName("address"                  ) var address                : String?                      = null,
//  @SerialName("badges"                   ) var badges                 : ArrayList<String>            = arrayListOf(),
//  @SerialName("badges_v2"                ) var badgesV2               : ArrayList<String>            = arrayListOf(),
//  @SerialName("brand_image"              ) var brandImage             : BrandImage?                  = BrandImage(),
//  @SerialName("categories"               ) var categories             : ArrayList<String>            = arrayListOf(),
//  @SerialName("city"                     ) var city                   : String?                      = null,
//  @SerialName("country"                  ) var country                : String?                      = null,
//  @SerialName("currency"                 ) var currency               : String?                      = null,
//  @SerialName("delivers"                 ) var delivers               : Boolean?                     = null,
//  @SerialName("delivery_highlight"       ) var deliveryHighlight      : Boolean?                     = null,
//  @SerialName("delivery_price_highlight" ) var deliveryPriceHighlight : Boolean?                     = null,
//  @SerialName("estimate"                 ) var estimate               : Int?                         = null,
//  @SerialName("estimate_box"             ) var estimateBox            : EstimateBox?                 = EstimateBox(),
//  @SerialName("estimate_range"           ) var estimateRange          : String?                      = null,
//  @SerialName("franchise"                ) var franchise              : String?                      = null,
    @SerialName("id") var id: String? = null,
//  @SerialName("location"                 ) var location               : ArrayList<Double>            = arrayListOf(),
    @SerialName("name") var name: String? = null,
//  @SerialName("online"                   ) var online                 : Boolean?                     = null,
//  @SerialName("price_range"              ) var priceRange             : Int?                         = null,
//  @SerialName("product_line"             ) var productLine            : String?                      = null,
//  @SerialName("promotions"               ) var promotions             : ArrayList<String>            = arrayListOf(),
//  @SerialName("rating"                   ) var rating                 : Rating?                      = Rating(),
    @SerialName("short_description") var shortDescription: String? = null,
//  @SerialName("show_wolt_plus"           ) var showWoltPlus           : Boolean?                     = null,
//  @SerialName("slug"                     ) var slug                   : String?                      = null,
//  @SerialName("tags"                     ) var tags                   : ArrayList<String>            = arrayListOf(),
//  @SerialName("venue_preview_items"      ) var venuePreviewItems      : ArrayList<VenuePreviewItems> = arrayListOf()

)