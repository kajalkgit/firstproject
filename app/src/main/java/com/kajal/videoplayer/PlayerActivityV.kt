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
        lateinit var player: SimpleExoPlayer
        lateinit var playerList: ArrayList<Video>
        var position: Int = -1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayervBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeLayout()
    }
    private fun initializeLayout(){
        when(intent.getStringExtra("class")){
            "AllVideos" ->{
                playerList = ArrayList()
                playerList.addAll(MainActivity.videoList)
            }
            "FoldersActivity" ->{
                playerList = ArrayList()
                playerList.addAll(FoldersActivity.currentFoldersVideo)
            }
        }
        createPlayer()
    }
    private fun createPlayer(){
        binding.videoTittle.text = playerList[position].title
        binding.videoTittle.isSelected = true
        player = SimpleExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val mediaItem = MediaItem.fromUri(playerList[position].artUri)
        player.setMediaItem(mediaItem)
        Log.d("mediaItemssss",mediaItem.toString())
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    }
