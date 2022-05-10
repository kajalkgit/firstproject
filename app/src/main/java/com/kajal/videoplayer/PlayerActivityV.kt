package com.kajal.videoplayer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.kajal.videoplayer.databinding.ActivityPlayerBinding
import com.kajal.videoplayer.databinding.ActivityPlayervBinding

class PlayerActivityV : AppCompatActivity() {
    private lateinit var binding: ActivityPlayervBinding

    companion object{
        lateinit var playerList: ArrayList<Video>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayervBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val player = SimpleExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val mediaItem = MediaItem.fromUri(MainActivity.videoList[0].artUri)
        player.setMediaItem(mediaItem)
        Log.d("mediaItemssss",mediaItem.toString())
        player.prepare()
        player.play()


    }
}