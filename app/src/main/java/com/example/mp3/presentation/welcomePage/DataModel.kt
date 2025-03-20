package com.example.mp3.presentation.welcomePage

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "data")
data class DataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val filePath: String
):Serializable



