package com.example.woltapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "venues")
data class Venue(

    @PrimaryKey
    @SerialName("id") var id: String = "",
    @SerialName("name") var name: String? = null,
    @SerialName("short_description") var shortDescription: String? = null,
    var isFavourite: Boolean = false,
)