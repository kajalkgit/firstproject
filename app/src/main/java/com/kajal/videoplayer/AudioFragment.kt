package com.kajal.videoplayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kajal.videoplayer.databinding.FragmentAudioBinding

class AudioFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_audio, container, false)


        val binding = FragmentAudioBinding.bind(view)
        binding.AudioRV.setHasFixedSize(true)
        binding.AudioRV.setItemViewCacheSize(13)
        binding.AudioRV.layoutManager = LinearLayoutManager(requireContext())
        Log.d("videolist====>>>", MainActivity.MusicListMA.toString())
        binding.AudioRV.adapter = MusicAdapter(requireContext(), MainActivity.MusicListMA)
        binding.TotalSongs.text  = "Total Audios: ${MainActivity.MusicListMA!!.size}"

        return view

    }


}