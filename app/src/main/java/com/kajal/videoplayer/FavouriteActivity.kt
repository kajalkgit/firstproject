package com.kajal.videoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kajal.videoplayer.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFavouriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)

        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButtonFA.setOnClickListener{finish()}
    }
}