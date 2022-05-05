package com.kajal.videoplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kajal.videoplayer.databinding.FoldersViewBinding


class FolderViewAdapter(private var context: Context,private val folderList: ArrayList<Folder>)
    :RecyclerView.Adapter<FolderViewAdapter.MyHolder>() {

//    class MyHolder(view: View): RecyclerView.ViewHolder(view)
//    {
//        var btn_edit : Button = view.findViewById(R.id.edit_btn)
//        val name : TextView = view.findViewById(R.id.nameF)
//      //  val add_btn = binding.add
////        val delete = binding.dltBtn
////        var root = binding.root
//
//    }


    class MyHolder(binding: FoldersViewBinding):RecyclerView.ViewHolder(binding.root)
    {
        val name = binding.nameF
        val edit = binding.editBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewAdapter.MyHolder {
//        return FolderViewAdapter.MyHolder()
       // val view = LayoutInflater.from(context).inflate(R.layout.folders_view,)
        return MyHolder(FoldersViewBinding.inflate(LayoutInflater.from(context),parent,false))
//        return MusicAdapter.MyHolder(
//            MusicViewBinding.inflate(
//                LayoutInflater.from(context),
//                parent,
//                false
//            )
//        )

    }
    override fun onBindViewHolder(holder: FolderViewAdapter.MyHolder, position: Int) { val folders = folderList[position]
        holder.name.text = folderList[position].name

        holder.name.isSelected = true

        holder.edit.setOnClickListener{
           val i= Intent(context,AddFolder::class.java)

            i .putExtra("Mode","E")
            i .putExtra("ID",folders.id)
            ContextCompat.startActivity(context,i,null)
        }


    }


    override fun getItemCount(): Int {

        return folderList.size
    }



}