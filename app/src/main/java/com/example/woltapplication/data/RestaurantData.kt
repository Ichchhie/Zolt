package com.example.woltapplication.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class RestaurantData(

//    @SerialName("created"               ) var created            : Created?            = Created(),
//    @SerialName("expires_in_seconds") var expiresInSeconds: Int? = null,
//    @SerialName("filtering"             ) var filtering          : Filtering?          = Filtering(),
//    @SerialName("how_search_works_link" ) var howSearchWorksLink : HowSearchWorksLink? = HowSearchWorksLink(),
//    @SerialName("how_search_works_url") var howSearchWorksUrl: String? = null,
//    @SerialName("name") var name: String? = null,
//    @SerialName("page_title") var pageTitle: String? = null,
    @SerialName("sections") var sections: ArrayList<Sections> = arrayListOf(),
//    @SerialName("show_large_title") var showLargeTitle: Boolean? = null,
//    @SerialName("show_map") var showMap: Boolean? = null,
//    @SerialName("sorting") var sorting: Sorting? = Sorting(),
//    @SerialName("track_id") var trackId: String? = null

)
