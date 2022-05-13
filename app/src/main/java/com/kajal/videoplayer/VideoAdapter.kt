package com.kajal.videoplayer

import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kajal.videoplayer.databinding.VideoViewBinding

class VideoAdapter(private val context: Context, private var videoList: ArrayList<Video>,private val isFolder:Boolean = false) : RecyclerView.Adapter<VideoAdapter.MyHolder>() {
    class MyHolder(binding: VideoViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val vName = binding.videoName
        val folder = binding.folderName
        val image = binding.videoMV
        val duration = binding.duration
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(VideoViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Log.d("imagess==>", videoList.toString())
        holder.vName.text = videoList[position].title
        holder.folder.text = videoList[position].folderName
        holder.duration.text = formatDuration(videoList[position].duration)
        Glide.with(context)
            .asBitmap()
            .load(videoList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.video_player).centerCrop())
            .into(holder.image)
        holder.root.setOnClickListener {
            when{
                isFolder ->{
                    sendIntent(pos= position, ref = "FoldersActivity")
                }
                else ->{
                    sendIntent(pos= position, ref = "AllVideos")

                }
            }

        }

    }

    override fun getItemCount(): Int {
        return videoList.size
    }
    private fun sendIntent(pos: Int, ref:String){
        PlayerActivityV.position = pos
        val intent = Intent(context, PlayerActivityV::class.java)
        intent.putExtra("class", ref)
        ContextCompat.startActivity(context, intent, null)
    }
}
