package com.kajal.videoplayer


import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.kajal.videoplayer.databinding.ActivityMainBinding as ActivityMainBinding1

import java.io.File
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding1
    private lateinit var musicAdapter: MusicAdapter
  //  var folderViewAdapter : FolderViewAdapter ?= null

    //db var use
//    var dbHandler:MyHelper ?= null
//    var folderList: List<Folder> = ArrayList<Folder>()
//    lateinit var recycler_folder : RecyclerView
//
//    lateinit var btn_add : Button
//
//   var linearLayoutManager : LinearLayoutManager ?= null

    companion object {
       //static object
        lateinit var MusicListMA: ArrayList<Music>

        lateinit var videoList: ArrayList<Video>
       var folderList: ArrayList<Folder>?=null
    }
    // var helper = MyHelper(applicationContext)
//    var db: SQLiteDatabase? = helper.readableDatabase



    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // requestRuntimePermission()
        setTheme(R.style.coolPinkNav)
        binding = ActivityMainBinding1.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (requestRuntimePermission())
        {
           initializeLayout()
//            Log.d("all videosssss", getAllVideo().toString())
//           folderList = ArrayList()
//            videoList = getAllVideo()

            setFragment(AudioFragment())
        }
        ////////////////////////////chhama// //////////////////////
//        Log.d("selectedidsss=>",selectedItem.toString())
        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId)
            {
                R.id.MediaView -> setFragment(VideosFragment())
                R.id.FoldersView -> setFragment(FoldersFragment())
                R.id.AudioView -> setFragment(AudioFragment())
                R.id.ShuffleView -> setFragment(ShuffleFragment())
            }
            return@setOnItemSelectedListener true

        }


//////////////////////////////////end//////////////////////////////////////
//        binding.shuffleBtn.setOnClickListener{
//            startActivity(Intent(this@MainActivity,PlayerActivity::class.java))
//
//        }
//        binding.favrtBtn.setOnClickListener{
//
//
//            startActivity(Intent(this@MainActivity,FavouriteActivity::class.java))
//        }

//       binding.playlistBtn.setOnClickListener{
//
//
//            startActivity(Intent(this@MainActivity,playlistActivity::class.java))
//        }

    }





        // recycler_folder =findViewById(R.id.recycler_folder)
        //  btn_add = findViewById(R.id.add_btn)


        //  fetchlist()

//        btn_add.setOnClickListener{
//            val i = Intent(applicationContext,AddFolder::class.java)
//            startActivity(i)
//        }
//
//        dbHandler = MyHelper(this)







//        binding.shuffleBtn.setOnClickListener {
//            val intent = Intent(this@MainActivity, PlayerActivity::class.java)
//            intent.putExtra("index",0)
//            intent.putExtra("class","MainActivity")
//            startActivity(intent)
//        }
//        binding.favrtBtn.setOnClickListener {
//
//
//            startActivity(Intent(this@MainActivity, FavouriteActivity::class.java))
//        }
//
//       binding.folderBtn.setOnClickListener {
//
//
//            startActivity(Intent(this@MainActivity, FolderActivity::class.java))
//        }




    private fun setFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentFL,fragment)
        transaction.disallowAddToBackStack() //to save space from different stack available in stack as we have to click thrice
        transaction.commit()

    }

//    private fun fetchlist(){
//        folderList = dbHandler!!.getAllFolder()
//        folderViewAdapter = FolderViewAdapter(folderList,applicationContext)
//        linearLayoutManager = LinearLayoutManager(applicationContext)
//        recycler_folder.layoutManager = linearLayoutManager
//        recycler_folder.adapter = folderViewAdapter
//        folderViewAdapter?.notifyDataSetChanged()
//    }


    //for req permission
    private fun requestRuntimePermission() :Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                13)
            return false
        }
        return true

    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray)
    {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults)
        if (requestCode == 13)
        {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)  //to add curly braces
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                initializeLayout()

//            folderList = ArrayList()
//            videoList = getAllVideo()
//           folderList = ArrayList()
            setFragment(VideosFragment())
        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 13 )

        }


    @RequiresApi(Build.VERSION_CODES.R)
    private fun initializeLayout() {


        Log.d("all videossssdfsdfdsss", getAllAudio().toString())

        folderList = ArrayList()
        MusicListMA = getAllAudio()
        videoList = getAllVideo()

     // folderList = ArrayList()
        binding.mediaRV.setHasFixedSize(true)
        binding.mediaRV.setItemViewCacheSize(13)
//        binding.mediaRV.layoutManager = LinearLayoutManager(this@MainActivity)
//        musicAdapter = MusicAdapter(this@MainActivity, MusicListMA)
//        binding.mediaRV.adapter = musicAdapter
//        binding.totalSongs.text = "Total Songs : " + musicAdapter.itemCount
//

    }

    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.R)
    private fun getAllAudio(): ArrayList<Music> {
        val temList = ArrayList<Music>()

        val temFolderList = ArrayList<String>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + " !=0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Audio.Media.BUCKET_ID,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
        )



        //use to pass only internal context of audio
        val cursor = this.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            MediaStore.Audio.Media.DEFAULT_SORT_ORDER,
            null)


        Log.d("cursor", cursor.toString());

        if (cursor != null) {
            if (cursor.moveToFirst())
                do {
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistsC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIdC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                     val folderC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.BUCKET_DISPLAY_NAME)).toString()
                     val folderIdC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.BUCKET_ID))

try {

    val uri = Uri.parse("content://media/external/audio/albumart") //using glide with this
    val artUriC = Uri.withAppendedPath(uri, albumIdC.toString()).toString()


    val music = Music(
        id = idC,
        title = titleC,
        album = albumC,
        artist = artistsC,
        folderName = folderC,
        path = pathC,
        duration = durationC,
        artUri = artUriC
    )

    val file = File(music.path)
    if (file.exists()) temList.add(music)

    //for adding folders

    if (!temFolderList.contains(folderC)) {
        temFolderList.add(folderC)
        folderList?.add(Folder(id = folderIdC, folderName = folderC))
    }
}catch (e:Exception){}





                } while (cursor.moveToNext())
            cursor.close()
        }

        return temList

    }

    override fun onResume() {
        super.onResume()
        binding.nowPlaying.visibility=View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!PlayerActivity.isPlaying && PlayerActivity.musicService != null) {
            PlayerActivity.musicService!!.stopForeground(true)
            PlayerActivity.musicService!!.mediaPlayer!!.release()
            PlayerActivity.musicService = null
            exitProcess(1)
        }
    }



    private fun getAllVideo(): ArrayList<Video>
    {
        val tempList = ArrayList<Video>()
        val tempFolderList = ArrayList<String>()
        val projection = arrayOf(
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.BUCKET_ID)
        val cursor = this.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Video.Media.DATE_ADDED + " DESC")
        if (cursor != null)
            if (cursor.moveToNext())
                do {
                    val titleC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val folderC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val folderIdC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID))
                    val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                    val durationC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                            .toLong()

                    try {
                        val file = File(pathC)
                        val artUri = Uri.fromFile(file)
                        val video = Video(
                            title = titleC,
                            id = idC,
                            folderName = folderC,
                            size = sizeC,
                            path = pathC,
                            artUri = artUri
                        )
                        if (file.exists()) tempList.add(video)

                        //for adding folders

                        if (!tempFolderList.contains(folderC)) {
                            tempFolderList.add(folderC)
                            folderList?.add(Folder(id = folderIdC, folderName = folderC))
                        }
                    } catch (e: Exception) {
                    }
                } while (cursor.moveToNext())
        cursor?.close()
        return tempList
    }
}


