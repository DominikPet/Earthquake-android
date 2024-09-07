package com.pero.earthquakeapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/"

interface EarthquakeApi {
    @GET("4.5_day.geojson")
    fun fetchItems(@Query("count") count: Int = 10)
            : Call<EarthquakeItem>
}