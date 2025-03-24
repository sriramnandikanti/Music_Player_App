package com.example.mp3

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mp3.presentation.welcomePage.DataModel

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dataModel: DataModel)

    @Update
    fun update(dataModel: DataModel)

    @Delete
    fun delete(dataModel: DataModel)

    @Query("delete from data")
    fun deleteAllNotes()

    @Query("select * from data order by title desc")
    fun getAllNotes(): LiveData<List<DataModel>>
}