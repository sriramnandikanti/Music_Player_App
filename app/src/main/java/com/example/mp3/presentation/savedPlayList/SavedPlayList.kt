package com.example.mp3.presentation.savedPlayList

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.example.mp3.R
import com.example.mp3.SongsDataBase
import com.example.mp3.data.repository.MainRepositoryImpl
import com.example.mp3.domain.use_case.welcomeMusicList.MyViewModelFactory
import com.example.mp3.domain.use_case.welcomeMusicList.WelcomePageViewModel

//Activity for showing saved playlist

class SavedPlayList : AppCompatActivity() {
    private lateinit var welcomePageViewModel: WelcomePageViewModel
    private var musicData: DataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saved_playlist)
        supportActionBar?.hide()
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.red)))
        window.statusBarColor=resources.getColor(R.color.red)
        val dao = SongsDataBase.getInstance(applicationContext).EventDao()
        val mainRepository = MainRepositoryImpl(dao)
        val myViewModelFactory = MyViewModelFactory(mainRepository)
        welcomePageViewModel =
            ViewModelProvider(this, myViewModelFactory).get(WelcomePageViewModel::class.java)

        welcomePageViewModel.getAllNotes().observe(this, Observer {
            val adapter= SavedPageAdapter(it,applicationContext, this)
            findViewById<RecyclerView>(R.id.saving_recycleview).adapter=adapter
        })

    }




    fun movetoMusicplayer(positiondata: DataModel) {
        val intent= Intent(this, MusicPlayer::class.java)
        intent.putExtra("data",positiondata)
        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent)
    }
}