package com.kajal.videoplayer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kajal.videoplayer.databinding.FragmentShuffleBinding


class ShuffleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shuffle, container, false)
         val binding = FragmentShuffleBinding.bind(view)
       binding.root.setOnClickListener{
           val intent = Intent(requireContext(),PlayerActivity::class.java)
           intent.putExtra("index",PlayerActivity.songPosition)
           intent.putExtra("class","ShuffleFragment")
           ContextCompat.startActivity(requireContext(),intent,null)
       }


        return view
    }



}