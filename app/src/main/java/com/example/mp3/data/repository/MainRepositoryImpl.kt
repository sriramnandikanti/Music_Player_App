package com.example.mp3.data.repository

import androidx.lifecycle.LiveData
import com.music.player.EventDao
import com.music.player.domain.repository.MainRepository
import com.music.player.presentation.welcomePage.DataModel

/* Class For all logical operation please add all operation here*/
class MainRepositoryImpl constructor(private var dao: EventDao
) : MainRepository {
    override fun insert(dataModel: DataModel) {

        dao.insert(dataModel)
    }

    override fun update(dataModel: DataModel) {

        dao.update(dataModel)
    }

    override fun delete(dataModel: DataModel) {

        dao.delete(dataModel)
    }

    override fun deleteAllNotes() {

        dao.deleteAllNotes()
    }

    override fun getAllNotes(): LiveData<List<DataModel>> {

        return  dao.getAllNotes()
    }

}