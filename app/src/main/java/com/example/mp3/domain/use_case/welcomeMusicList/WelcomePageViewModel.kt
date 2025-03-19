package com.example.mp3.domain.use_case.welcomeMusicList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mp3.domain.repository.MainRepository

// ViewModel for getting Music List from mobile and  Connecting UseCase and Activity of Welcome Page

class WelcomePageViewModel(private val userRepository: MainRepository) : ViewModel() {

    fun insert(dataModel: DataModel) {

        userRepository.insert(dataModel)
    }

    fun update(dataModel: DataModel) {

        userRepository.update(dataModel)
    }

    fun delete(dataModel: DataModel) {

        userRepository.delete(dataModel)
    }

    fun deleteAllNotes() {

        userRepository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<DataModel>> {

        return  userRepository.getAllNotes()
    }


}


class MyViewModelFactory(private val userRepository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WelcomePageViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}