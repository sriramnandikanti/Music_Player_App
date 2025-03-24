package com.example.mp3.presentation.welcomePage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3.R
import com.example.mp3.domain.use_case.welcomeMusicList.WelcomePageViewModel
import com.example.mp3.presentation.musicPlayer.MusicPlayer
import com.example.mp3.presentation.savedPlayList.SavedPlayList

//Activity for  Welcome Page
private const val STORAGE_PERMISSION_REQUEST_CODE=1001
class WelcomePage : AppCompatActivity() {
    private  lateinit var menuItem: MenuItem
    private  lateinit var searchView: SearchView
    lateinit var likeBtn: ImageView
    private lateinit var newArrayList:ArrayList<DataModel>
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var musicTracks: List<DataModel>
    private lateinit var viewModel: WelcomePageViewModel


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
//        val bar: ActionBar? = actionBar
//        bar.setBackgroundDrawable( Color());
        supportActionBar?.setIcon(R.drawable.baseline_menu_24)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.red)))
        supportActionBar?.title = "  Libary"
        window.statusBarColor=resources.getColor(R.color.red)


        if (checkStoragePermission()) {
            songsFromMusicAdapter()
//            musicSongFromDevicedapterAudioFiles()
        } else {
            requestStoragePermission()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
//        return true
        var menuItem=menu!!.findItem(R.id.search_action)
        var searchview: SearchView =menuItem.actionView as SearchView
        val searchIcon =
            searchview.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)
        searchview.maxWidth= Int.MAX_VALUE
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                musicAdapter.filter(newText.toString())
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fav_icon->{
                val intent= Intent(this, SavedPlayList::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext,"dat", Toast.LENGTH_SHORT).show()
                true
            }
            /*R.id.search_action ->{
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val searchView=item.actionView as SearchView
                // Configure the search view (e.g., add listeners)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        // Handle query submission
                        val data=getMusicFromDevice(applicationContext)
                        MusicAdatsater.filter(query.toString(),data)
                        return true
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        // Handle query text change


                        val data=getMusicFromDevice(applicationContext)
                        MusicAdatsater.filter(newText.toString(),data)
//                        Toast.makeText(applicationContext,"",Toast.LENGTH_SHORT).show()
                        return true
                    }
                })

                true
            }*/
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun songsFromMusicAdapter() {
        val data=getMusicFromDevice(applicationContext)
        musicAdapter=MusicAdapter(data,applicationContext,this)
        findViewById<RecyclerView>(R.id.welcome_page_recycler).adapter=musicAdapter
    }


    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                songsFromMusicAdapter()
            } else {
                Toast.makeText(
                    this,
                    "Permission denied. Cannot access audio files without storage permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun movetoMusicplayer(positiondata: DataModel) {
        val intent= Intent(this, MusicPlayer::class.java)
        intent.putExtra("data",positiondata)
        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent)
    }


}