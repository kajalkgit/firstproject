package com.kajal.videoplayer

import android.media.MediaMetadataRetriever
import java.util.concurrent.TimeUnit

data class Music(val id: String, val title: String, val duration: Long=0, val album:String, val artist:String, val path:String,
                                   val artUri: String)

class Folders  {
    var id:Int =0
     var name:String= ""
    //var folder: ArrayList<Music> =[ ]
}

class MediaPlaylist{
    var ref: ArrayList<Folders> = ArrayList()
}

fun formatDuration(duration: Long):String{
    val minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
    val seconds =(TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS)-
            minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES))

   return String.format("%02d:%02d", minutes,seconds)

}


fun getImgArt(path: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(path)
    return retriever.embeddedPicture
}

 fun setSongPosition(increment: Boolean) {
    if (increment)
    {
        if(PlayerActivity.musicListPA.size-1 == PlayerActivity.songPosition)
            PlayerActivity.songPosition =0
        else ++PlayerActivity.songPosition
    } else{
        if(0 == PlayerActivity.songPosition)
            PlayerActivity.songPosition = PlayerActivity.musicListPA.size -1
        else --PlayerActivity.songPosition
    }
}


