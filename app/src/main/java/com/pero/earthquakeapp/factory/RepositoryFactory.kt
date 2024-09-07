package com.pero.earthquakeapp.factory

import android.content.Context
import com.pero.earthquakeapp.dao.EarthquakeSqlHelper

fun getEarthquakeRepository(context: Context?) = EarthquakeSqlHelper(context)