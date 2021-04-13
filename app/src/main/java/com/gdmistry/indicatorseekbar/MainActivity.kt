package com.gdmistry.indicatorseekbar

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.gdmistry.indicatorseekbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Use like this
        binding.indicatorSeeker.setMax(200)
        binding.indicatorSeeker.makeRTL()

        binding.indicatorSeeker.setIndicatorTextAttribs(12f, android.R.color.holo_red_dark, null)

        binding.indicatorSeeker.callback = object : IndicatorSeeker.Callback {
            override fun onProgressChanged(seeker: SeekBar?, progress: Int, fromUser: Boolean) {
                //Your task
            }
        }
    }
}