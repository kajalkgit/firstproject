package com.kajal.videoplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kajal.videoplayer.databinding.FragmentFoldersBinding

class FoldersFragment : Fragment() {



    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, saveInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_folders, container, false)
        val binding = FragmentFoldersBinding.bind(view)

        binding.FoldersRV.setHasFixedSize(true)
        binding.FoldersRV.setItemViewCacheSize(13)

        binding.FoldersRV.layoutManager = LinearLayoutManager(requireContext())
        binding.FoldersRV.adapter =
            MainActivity.folderList?.let { FoldersAdapter(requireContext(), it) }
//        binding.FoldersRV.adapter =
        Log.d("foldersssss==>>>>>>",MainActivity.folderList!!.toString())
//            MainActivity.folderList?.let { FoldersAdapter(requireContext(), it) }
       binding.totalFolders.text  = "Total Folders: ${MainActivity.folderList?.size}"


        binding.addBtn.setOnClickListener{
            requireActivity().run {
                val intent =Intent(this,PlayerActivity::class.java)
                intent.putExtra("index",0)
                intent.putExtra("class","FoldersFragment")
                startActivity(intent)
            }


       }
        return view


    }
}



