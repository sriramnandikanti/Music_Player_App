package com.example.mp3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.music.player.presentation.welcomePage.DataModel

@Database(entities = [DataModel::class], version = 1)
abstract class SongsDataBase:RoomDatabase() {
    abstract fun EventDao(): EventDao

    companion object {
        private var instance: SongsDataBase? = null

        @Synchronized
        fun getInstance(ctx: Context): SongsDataBase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, SongsDataBase::class.java,
                    "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }
}