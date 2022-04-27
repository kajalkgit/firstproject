package com.kajal.videoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kajal.videoplayer.databinding.ActivityPlaylistBinding

class playlistActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlaylistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButtonPLA.setOnClickListener{
            finish()
        }
    }
}