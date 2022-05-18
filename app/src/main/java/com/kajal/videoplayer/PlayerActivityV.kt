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
        initializeBinding()
    }
    private fun initializeLayout(){
        when(intent.getStringExtra("class")){
            "AllVideos" ->{
                playerList = ArrayList()
                playerList.addAll(MainActivity.videoList)
                createPlayer()
            }
            "FoldersActivity" ->{
                playerList = ArrayList()
                playerList.addAll(FoldersActivity.currentFoldersVideo)
                createPlayer()
            }
        }

    }
    private fun initializeBinding(){
        binding.backBtnPA.setOnClickListener {
            finish()
        }
        binding.playpauseBtnPA.setOnClickListener {
              if(player.isPlaying) pauseVideo()
            else playVideo()
        }
        binding.nextBtnPA.setOnClickListener { nextPrevVideo() }
        binding.previousBtnPA.setOnClickListener { nextPrevVideo(isNext = false) }
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
        playVideo()
    }


    private fun playVideo(){
        binding.playpauseBtnPA.setImageResource(R.drawable.pause_icon)
        player.play()
    }
    private fun pauseVideo(){
        binding.playpauseBtnPA.setImageResource(R.drawable.play_icon)
        player.pause()
    }
    private fun nextPrevVideo(isNext: Boolean = true){
        player.release()
        if(isNext) setPosition()
        else setPosition(isIncrement = false)
        createPlayer()
    }
    private fun setPosition(isIncrement: Boolean = true){
        if(isIncrement){
            if(playerList.size -1 == position)
                position = 0
            else position++
        }else{
            if(position  == 0)
                position = playerList.size -1
            else position--
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    }
