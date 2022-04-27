package com.kajal.videoplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kajal.videoplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(), ServiceConnection, MediaPlayer.OnCompletionListener {


    companion object {
        lateinit var musicListPA: ArrayList<Music>
        var songPosition: Int = 0
       // var mediaPlayer: MediaPlayer? = null

        var isPlaying:Boolean = false
        var musicService: MusicService? = null
        lateinit var binding: ActivityPlayerBinding


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, MusicService:: class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent) //for starting service

        initializeLayout()

        //back btn functionality
        binding.backBtnPA.setOnClickListener()
        {
            finish()
        }

        binding.playpauseBtnPA.setOnClickListener{
            if(isPlaying) pauseMusic()
            else playMusic()

        }

        //previus button binding
        binding.previousBtnPA.setOnClickListener{
          prevNextSong(increment = false)
        }

        binding.nextBtnPA.setOnClickListener{
            prevNextSong(increment = true)
        }
        binding.seekbarPA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar : SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) musicService!!.mediaPlayer!!.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit

            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        })
    }

    private fun setLayout()
    {
        Glide.with(this)
            .load(musicListPA[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.musicplayericon).centerCrop())
            .into(binding.songImgPA)
        binding.songNamePA.text = musicListPA[songPosition].title


    }
    private fun createMediaPlayer()
    {

        //fucn to play music
        try {
            if (musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
            isPlaying = true
            binding.playpauseBtnPA.setIconResource(R.drawable.pause_icon)
            musicService!!.showNotification(R.drawable.pause_icon)
            binding.tvSeekBarStarting.text =  formatDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
            binding.tvSeekBarEnd.text =  formatDuration(musicService!!.mediaPlayer!!.duration.toLong())
            binding.seekbarPA.progress = 0 //inittiall progress
            binding.seekbarPA.max = musicService!!.mediaPlayer!!.duration


            musicService!!.mediaPlayer!!.setOnCompletionListener  (this) //start on song complete
        }
        catch (e:Exception) { return }

    }
    private fun initializeLayout()
    {
        songPosition = intent.getIntExtra("index", 0)
        when (intent.getStringExtra("class")) {               //add music to adapter
            "MusicAdapter" -> {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                setLayout()
                createMediaPlayer()
            }
            "MainActivity"->{
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                musicListPA.shuffle()
                setLayout()
                createMediaPlayer()
            }
        }
    }

    private fun playMusic() {
        binding.playpauseBtnPA.setIconResource(R.drawable.pause_icon)
        musicService!!.showNotification(R.drawable.pause_icon)
        isPlaying = true
        musicService!!.mediaPlayer!!.start()

    }

    private fun pauseMusic() {
      binding.playpauseBtnPA.setIconResource(R.drawable.play_icon)
        musicService!!.showNotification(R.drawable.pause_icon)
        isPlaying =false
        musicService!!.mediaPlayer!!.pause()
    }

    //prev btn feature
    private fun prevNextSong(increment: Boolean)
    {
        if(increment)
        {
           setSongPosition(increment = true)
            setLayout()
            createMediaPlayer()
        }else{
             setSongPosition(increment = false)
            setLayout()
            createMediaPlayer()

        }
    }


    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.seekBarSetup()  //for call through musicService


    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null

    }

    override fun onCompletion(p0: MediaPlayer?) {
        setSongPosition(increment = true)
        createMediaPlayer()
        try {
            setLayout()
         }catch (e:Exception){return}
    }
}
