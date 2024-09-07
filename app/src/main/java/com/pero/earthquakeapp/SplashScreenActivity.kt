package com.pero.earthquakeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.pero.earthquakeapp.api.InfoWorker
import com.pero.earthquakeapp.databinding.ActivitySplashScreenBinding
import com.pero.earthquakeapp.framework.applyAnimation
import com.pero.earthquakeapp.framework.callDelayed
import com.pero.earthquakeapp.framework.getBooleanPreference
import com.pero.earthquakeapp.framework.isOnline
import com.pero.earthquakeapp.framework.startActivity

const val DATA_IMPORTED = "com.pero.earthquakeapp.data_imported"
private const val DELAY = 3000L

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun redirect() {
        if(getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY){
                startActivity<HostActivity>()
            }
        }
        else if(isOnline()){
            WorkManager.getInstance(this).apply {
                enqueueUniqueWork(
                    DATA_IMPORTED,
                    ExistingWorkPolicy.KEEP,
                    OneTimeWorkRequest.from(InfoWorker::class.java)
                )
            }
        }
        else{
            binding.tvSplash.text = "No internet"
            callDelayed(DELAY) {
                finish()
            }
        }
    }

    private fun startAnimations() {
        binding.ivSplash.applyAnimation(R.anim.left_and_right)
        binding.tvSplash.applyAnimation(R.anim.blink)
    }


}