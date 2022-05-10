package com.kajal.videoplayer

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kajal.videoplayer.databinding.ActivityFoldersBinding
import java.io.File

class FoldersActivity :AppCompatActivity() {

    companion object{
        lateinit var currentFoldersVideo: ArrayList<Video>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFoldersBinding.inflate(layoutInflater)
        setTheme(R.style.coolPinkNav)
        setContentView(binding.root)

        val position = intent.getIntExtra("position",0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = MainActivity.folderList!![position].folderName
        currentFoldersVideo = getAllVideo(MainActivity.folderList!![position].id)
        binding.VideoRVFA.setHasFixedSize(true)
        binding.VideoRVFA.setItemViewCacheSize(13)
        binding.VideoRVFA.layoutManager = LinearLayoutManager(this@FoldersActivity)
        binding.VideoRVFA.adapter =
            VideoAdapter(this@FoldersActivity,currentFoldersVideo)
        binding.totalVideosFA.text = "Total Videos: ${currentFoldersVideo.size}"

    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true

    }

    private fun getAllVideo(folderId: String): ArrayList<Video> {
        val tempList = ArrayList<Video>()
        val selection =  MediaStore.Video.Media.BUCKET_ID + " like? "
       // val tempFolderList = ArrayList<String>()
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
            selection,
            arrayOf(folderId),
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
//                    val folderIdC =
//                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID))
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


                    } catch (e: Exception) {
                    }
                } while (cursor.moveToNext())
        cursor?.close()
        return tempList
    }


}