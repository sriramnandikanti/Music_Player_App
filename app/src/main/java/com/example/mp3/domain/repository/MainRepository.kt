package com.example.mp3.domain.repository

import androidx.lifecycle.LiveData

/* Class For all logical operation please add all operation here and use in in MainRepositoryImpl*/
interface MainRepository {
    fun insert(dataModel: DataModel)

    fun update(dataModel: DataModel)

    fun delete(dataModel: DataModel)

    fun deleteAllNotes()

    fun getAllNotes(): LiveData<List<DataModel>>
}