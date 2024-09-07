package com.pero.earthquakeapp.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.pero.earthquakeapp.EARTHQUAKE_PROVIDER_CONTENT_URI
import com.pero.earthquakeapp.InfoReceiver
import com.pero.earthquakeapp.framework.sendBroadcast
import com.pero.earthquakeapp.model.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InfoFetcher(private val context: Context) {

    private val earthquakeApi: EarthquakeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        earthquakeApi = retrofit.create(EarthquakeApi::class.java)
    }

    fun fetchItems(count: Int) {
        val request = earthquakeApi.fetchItems(count = count)

        request.enqueue(object: Callback<EarthquakeItem>{
            override fun onResponse(
                call: Call<EarthquakeItem>,
                response: Response<EarthquakeItem>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { populateItems(it) }
                } else {
                    Log.e(javaClass.name, "Failed to fetch earthquake data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<EarthquakeItem>, t: Throwable) {
                Log.e(javaClass.name, "Failed to fetch earthquake data", t)
            }

        })
    }

    private fun populateItems(earthquakeItem: EarthquakeItem) {
        val items = mutableListOf<Item>()

        earthquakeItem.features.forEach { feature ->
            val title = feature.properties?.title
            val mag = feature.properties?.mag
            val place = feature.properties?.place

            val values = ContentValues().apply {
                put(Item::title.name, title)
                put(Item::mag.name, mag)
                put(Item::place.name, place)
            }

            context.contentResolver.insert(
                EARTHQUAKE_PROVIDER_CONTENT_URI,
                values
            )
            /*if (mag != null && place != null) {
                items.add(Item(
                    null,
                    title,
                    mag,
                    place,
                    false
                ))
            }*/
        }
        context.sendBroadcast<InfoReceiver>()
    }
}
