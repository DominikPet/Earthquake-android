package com.pero.earthquakeapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pero.earthquakeapp.framework.setBooleanPreference
import com.pero.earthquakeapp.framework.startActivity

class InfoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()
    }
}