package com.kajal.videoplayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kajal.videoplayer.databinding.FragmentVideoBinding

class VideosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, saveInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)


        val binding = FragmentVideoBinding.bind(view)
        binding.VideoRV.setHasFixedSize(true)
        binding.VideoRV.setItemViewCacheSize(13)
        binding.VideoRV.layoutManager = LinearLayoutManager(requireContext())
        Log.d("videolist====>>>", MainActivity.videoList.toString())
        binding.VideoRV.adapter = VideoAdapter(requireContext(), MainActivity.videoList)
        return view
    }
}