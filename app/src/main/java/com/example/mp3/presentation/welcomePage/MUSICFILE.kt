package com.example.mp3.presentation.welcomePage

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore

@SuppressLint("Range")
fun getMusicFromDevice(context: Context): List<DataModel> {
    val musicSongFromDevice = mutableListOf<DataModel>()

    val selectionOrder = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATA
    )
    val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"

    val dataSavingsList = context.contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selectionOrder,
        null,
        sortOrder
    )

    dataSavingsList?.use {
        while (it.moveToNext()) {
            val id = it.getLong(it.getColumnIndex(MediaStore.Audio.Media._ID))
            val title = it.getString(it.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val artist = it.getString(it.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val duration = it.getLong(it.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val filePath = it.getString(it.getColumnIndex(MediaStore.Audio.Media.DATA))

            musicSongFromDevice.add(
                DataModel(id, title, artist,duration, filePath)
            )
        }
    }

    return musicSongFromDevice
}