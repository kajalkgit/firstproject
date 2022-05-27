package com.kajal.videoplayer

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kajal.videoplayer.databinding.ActivityFoldersBinding
import java.io.File

class FoldersActivity : AppCompatActivity() {

    companion object {
        lateinit var currentFoldersVideo: ArrayList<Video>
        lateinit var currentFoldersAudio: ArrayList<Music>
//        lateinit var folderList: ArrayList<Folder>

    }
    var folderList: ArrayList<Folder>?=null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFoldersBinding.inflate(layoutInflater)
        setTheme(R.style.coolPinkNav)
        setContentView(binding.root)
        val position = intent.getIntExtra("position", 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Log.d("datass",MainActivity.folderList!!.toString())
        supportActionBar?.title = MainActivity.folderList!![position].folderName
        currentFoldersVideo = getAllVideo(MainActivity.folderList!![position].id)
        currentFoldersAudio = getAllAudio(MainActivity.folderList!![position].id)
        binding.VideoRVFA.setHasFixedSize(true)
        binding.AudioRVFA.setHasFixedSize(true)
        binding.AudioRVFA.setItemViewCacheSize(13)
        binding.VideoRVFA.setItemViewCacheSize(13)
        binding.VideoRVFA.layoutManager = LinearLayoutManager(this@FoldersActivity)
        binding.AudioRVFA.layoutManager = LinearLayoutManager(this@FoldersActivity)
        binding.VideoRVFA.adapter = VideoAdapter(this@FoldersActivity, currentFoldersVideo)
        binding.AudioRVFA.adapter = MusicAdapter(this@FoldersActivity, currentFoldersAudio)
        binding.Folders.adapter = folderList?.let { FoldersAdapter(this@FoldersActivity, it) }
        Log.d("folderlist", folderList.toString())
        //  binding.totalVideosFA.text = "Total Videos: ${currentFoldersVideo.size}"
        // binding.totalAudioFA.text = "Total Audios: ${currentFoldersAudio.size}"

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true

    }


    private fun getAllVideo(folderId: String): ArrayList<Video> {
        val tempList = ArrayList<Video>()
        val selection = MediaStore.Video.Media.BUCKET_ID + " like? "
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
                    val folderIdC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID))
                    val folderIdfc = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.BUCKET_ID))
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
                        if (file.exists())
                            tempList.add(video)


                    } catch (e: Exception) {
                        if(idC==null){
                            Log.d("foldersss",folderIdC+" "+folderC)
                        val folder = Folder(id = folderIdC, folderName = folderC)
                        folderList?.add(folder)}
                    }
                } while (cursor.moveToNext())
        cursor?.close()
        return tempList
    }


    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.R)
    private fun getAllAudio(folderId: String): ArrayList<Music> {
        val temList = ArrayList<Music>()
        val tempFolderList = ArrayList<String>()

        val selection = MediaStore.Audio.Media.BUCKET_ID + " like? "
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
            arrayOf(folderId),
            MediaStore.Audio.Media.DEFAULT_SORT_ORDER,
            null
        )


        Log.d("cursor", cursor.toString());

        if (cursor != null) {
            if (cursor.moveToFirst())
                do {
                    val titleC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistsC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIdC =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                            .toString()
                    val folderC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.BUCKET_DISPLAY_NAME))
                    val folderIdC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.BUCKET_ID))

                    try {
                        val uri =
                            Uri.parse("content://media/external/audio/albumart") //using glide with this
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
                        if (file.exists())
                            temList.add(music)
                    } catch (e: Exception) {
                        Log.d("albums",idC)
                        if(idC==null){
                            Log.d("foldersssnew",folderIdC+" "+folderC)

                            val folder = Folder(id = folderIdC, folderName = folderC)
                        folderList?.add(folder)}
                    }
//

                } while (cursor.moveToNext())
            cursor.close()
        }

        return temList

    }


}