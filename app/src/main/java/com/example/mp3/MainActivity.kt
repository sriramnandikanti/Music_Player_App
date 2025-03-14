package com.example.mp3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MP3ListAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mp3Adapter: MP3ListAdapter
    private var mp3Files: ArrayList<MP3File> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        mp3Adapter = MP3ListAdapter(mp3Files, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mp3Adapter

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        } else {
            loadMP3Files()
        }
    }

    private fun loadMP3Files() {
       /* val contentResolver = contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val title =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val artist =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val filePath =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val mp3File = MP3File(title, artist, filePath)
                mp3Files.add(mp3File)
            } while (cursor.moveToNext())
            cursor.close()
            mp3Adapter.notifyDataSetChanged()
        }*/
            val contentResolver = contentResolver
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
            )
            val cursor = contentResolver.query(uri, projection, null, null, null)
            cursor?.use { cursor ->
                val titleColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val filePathColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                val durationColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                while (cursor.moveToNext()) {
                    val title = cursor.getString(titleColumnIndex)
                    val artist = cursor.getString(artistColumnIndex)
                    val filePath = cursor.getString(filePathColumnIndex)
                    val duration = cursor.getInt(durationColumnIndex)
                    val mp3File = MP3File(title, artist, filePath, duration)
                    mp3Files.add(mp3File)
                }
                mp3Adapter.notifyDataSetChanged()
            }

    }

   /* private fun loadMP3Files() {
        val downloadsDirectory = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString())
        val mp3Files = downloadsDirectory.listFiles { file ->
            file.isFile && file.extension.equals("mp3", ignoreCase = true)
        }
        mp3Files?.forEach { mp3File ->
            // Process each MP3 file
            val title = mp3File.nameWithoutExtension
            val artist = "Unknown" // You may extract artist information if available
            val filePath = mp3File.absolutePath
            val mp3FileObject = MP3File(title, artist, filePath)
            // Add mp3FileObject to your list or adapter
        }
    }*/

    /*private fun loadMP3Files() {
        val downloadsDirectory = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString())
        val mp3Files = downloadsDirectory.listFiles { file ->
            file.isFile && file.extension.equals("mp3", ignoreCase = true)
        }
        val mp3FileList = ArrayList<MP3File>() // Define a list to store MP3File objects
        mp3Files?.forEach { mp3File ->
            // Process each MP3 file
            val title = mp3File.nameWithoutExtension
            val artist = "Unknown" // You may extract artist information if available
            val filePath = mp3File.absolutePath
            val mp3FileObject = MP3File(title, artist, filePath)
            mp3FileList.add(mp3FileObject) // Add MP3File object to the list
        }
        // Assuming mp3Adapter is your RecyclerView adapter
        mp3Adapter.submitList(mp3FileList) // Submit the list to the adapter
    }*/

/*    private fun loadMP3Files() {
        val downloadsDirectory = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString())
        val mp3Files = downloadsDirectory.listFiles { file ->
            file.isFile && file.extension.equals("mp3", ignoreCase = true)
        }
        val mp3FileList = ArrayList<MP3File>()
        mp3Files?.forEach { mp3File ->
            // Process each MP3 file
            val title = mp3File.nameWithoutExtension
            val artist = "Unknown"
            val filePath = mp3File.absolutePath
            val mp3FileObject = MP3File(title, artist, filePath)
            mp3FileList.add(mp3FileObject)
        }
        // Assuming mp3Adapter is your RecyclerView adapter
        mp3Adapter.updateData(mp3FileList) // Update the adapter's data
    }*/




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadMP3Files()
        }
    }

    override fun onItemClick(position: Int) {
        val selectedMP3File = mp3Files[position]
        val intent = Intent(this, MP3PlayerActivity::class.java)
        intent.putExtra("mp3File", selectedMP3File)
        startActivity(intent)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
    }
}
