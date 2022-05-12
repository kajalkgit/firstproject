package com.kajal.videoplayer

import android.net.Uri
import java.util.concurrent.TimeUnit

data class Video(val id: String, val title: String, val duration: Long=0, val folderName: String, val size: String, val path: String, val artUri: Uri)


data class Folder(val id: String, val folderName: String)

fun formatDuration(duration: Long):String{
    val minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
    val seconds =(TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS)-
            minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES))

    return String.format("%02d:%02d", minutes,seconds)

}
