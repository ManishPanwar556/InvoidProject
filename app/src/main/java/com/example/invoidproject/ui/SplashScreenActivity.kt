package com.example.invoidproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.invoidproject.databinding.ActivitySplashScreenBinding
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var timer: Timer
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val handler = Handler(Looper.myLooper()!!)
        val update = Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        timer = Timer()

        /** delay for starting MainActivity **/

        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }

        }, 400, 6000)

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}