package com.pero.earthquakeapp.api

import com.google.gson.annotations.SerializedName

data class Metadata(

    @SerializedName("generated" ) var generated : Long?    = null,
    @SerializedName("url"       ) var url       : String? = null,
    @SerializedName("title"     ) var title     : String? = null,
    @SerializedName("status"    ) var status    : Int?    = null,
    @SerializedName("api"       ) var api       : String? = null,
    @SerializedName("count"     ) var count     : Int?    = null

)
