package com.kajal.videoplayer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.kajal.videoplayer.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var videoList: ArrayList<Video>
         var folderList: ArrayList<Folder>?=null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.coolPinkNav)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (requestRuntimePermission()) {
            Log.d("all videosssss", getAllVideo().toString())
             folderList = ArrayList()
            videoList = getAllVideo()

            setFragment(VideosFragment())
        }
        ////////////////////////////chhama// //////////////////////
//        Log.d("selectedidsss=>",selectedItem.toString())
        binding.bottomNav.setOnItemSelectedListener {

//                val selectedItemId: Int = binding.bottomNav.selectedItemId
//                val selectedItem: MenuItem = binding.bottomNav.menu.findItem(selectedItemId)
//                Log.d("njdc",selectedItem.toString())
//                if(selectedItem.toString() == "All Folders"){
//
//                    Toast.makeText(this@MainActivity, "Item Clicked on floder", Toast.LENGTH_SHORT).show()}
//
//        else{
//            Toast.makeText(this@MainActivity, "Item Clicked on media", Toast.LENGTH_SHORT).show()}

            when (it.itemId) {
                R.id.MediaView -> setFragment(VideosFragment())
                R.id.FoldersView -> setFragment(FoldersFragment())
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

    private fun setFragment(fragment: Fragment) {
        val transcation = supportFragmentManager.beginTransaction()
        transcation.replace(R.id.fragmentFL, fragment)
        transcation.disallowAddToBackStack()
        transcation.commit()

    }


    //for req permission
    private fun requestRuntimePermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                13
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
        if (requestCode == 13) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            folderList = ArrayList()
            videoList = getAllVideo()
            setFragment(VideosFragment())
        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                13
            )

    }



    private fun getAllVideo(): ArrayList<Video> {
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
            MediaStore.Video.Media.BUCKET_ID

        )
        val cursor = this.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Video.Media.DATE_ADDED + " DESC"
        )
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
