package com.pero.earthquakeapp.model

data class Item(
    var _id: Long?,
    val title: String?,
    var mag: Double?,
    var place: String?,
    var read: Boolean
)
