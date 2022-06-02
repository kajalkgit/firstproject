package com.kajal.videoplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kajal.videoplayer.databinding.ActivityFolderBinding

class FolderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFolderBinding

    var adapter : FolderViewAdapter ?=null

    companion object{
        // var mediaPlaylist:MediaPlaylist = MediaPlaylist()
        lateinit var folderList: ArrayList<Folders>
    }

    var dbHandler:MyHelper ?= null
    //   var folderList: List<Folder>? =null
    lateinit var recycler_folder : RecyclerView

    lateinit var btn_add : Button

    var linearLayoutManager : LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)
        binding = ActivityFolderBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_folder)
        //  setContentView(R.layout.activity_folder)


        recycler_folder = findViewById(R.id.rv_list)
        btn_add = findViewById(R.id.add_btn)

        dbHandler = MyHelper(this)
        fetchlist()

        binding.backButtonPLA.setOnClickListener {
            finish()
        }
        btn_add.setOnClickListener{
            Log.d("abcd","sdscnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
            val i = Intent(this,AddFolder::class.java)
            startActivity(i)
        }
        // binding..setHasFixedSize(true)
        //  binding.recyclerFolder.setItemViewCacheSize(13)
        // binding.recyclerFolder.layoutManager = GridLayoutManager(this@FolderActivity, 3)
        // adapter = FolderViewAdapter(this, folderList = mediaPlaylist.ref)
        //  binding.recyclerFolder.adapter = adapter



//        binding.addBtn.setOnClickListener{
//            customAletDialog()
//        }
        //   fetchlist()

        Log.d("fectchoi => ",fetchlist().toString())

        //addFolder calling from anther activity

//
//        fetchlist()
//        btn_add.setOnClickListener{
//            val i = Intent(applicationContext,AddFolder::class.java)
//            startActivity(i)
//        }



        //mus  supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    private fun fetchlist(){
        Log.d("handlers",dbHandler!!.getAllFolder().toString())
        folderList = dbHandler!!.getAllFolder()
        adapter = FolderViewAdapter(this, folderList)

        linearLayoutManager = LinearLayoutManager(applicationContext)
        recycler_folder.layoutManager = linearLayoutManager
        recycler_folder.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

//        private fun customAletDialog(){
//
//            val customDialog = LayoutInflater.from(this).inflate(R.layout.add_folder_dialog,binding.root,false)
//            var binder = AddFolderDialogBinding.bind(customDialog)
//
//            val builder = MaterialAlertDialogBuilder(this)
//            builder.setView(customDialog)
//                .setTitle("Folder Details")
//                .setPositiveButton("ADD"){dialog,_ ->
//                    val folderName = binder.foldername.text
//                   // val createdBy = binder.yourName.text
//                    if(folderName != null)
//                        if(folderName.isNotEmpty())
//                        {
//                          //  addFolder(folderName.toString())
//                            AddFolder().folderName.toString()
//                        }
//                    dialog.dismiss()
//                }.show()
//        }

//    private fun addFolder(name: String)
//    {
//        var folderExits = false
//        for(i in mediaPlaylist.ref)
//        {
//            if(name.equals(i.name))
//            {
//                folderExits = true
//                break
//            }
//        }
//        if(folderExits) Toast.makeText(this,"folder Exits",Toast.LENGTH_SHORT).show()
//            else{
//                val tempfolder = Folder()
//                tempfolder.name =name
//               //  tempfolder.folder = ArrayList()
//            val calendar = Calendar.getInstance().time
//            val sdf = SimpleDateFormat("dd:MMM:yyyy",Locale.ENGLISH)
//
//          //  tempfolder.createdOn = sdf.format(calendar)
//            mediaPlaylist.ref.add(tempfolder) //12:24
//
//            }
//       }
}


