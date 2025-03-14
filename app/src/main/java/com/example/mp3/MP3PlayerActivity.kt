package com.example.mp3

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MP3PlayerActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playPauseButton: ImageButton
    private lateinit var seekBar: SeekBar
    private lateinit var currentTimeTextView: TextView
    private lateinit var durationTextView: TextView
    private var isPlaying: Boolean = false
    private var handler = Handler()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mp3_player)

//        val mp3File = intent.getParcelableExtra("mp3File", MP3File::class.java)
        val mp3File = intent.getParcelableExtra<MP3File>("mp3File")
        var title: TextView = findViewById<TextView?>(R.id.titleTextView)
        title.text = mp3File?.title

        var musicImageView: ImageView = findViewById(R.id.musicImageView)
        musicImageView.setImageResource(R.drawable.ic_launcher_background)

        val mediaPlayerUri = Uri.parse(mp3File?.filePath)
        mediaPlayer = MediaPlayer.create(this, mediaPlayerUri)

        playPauseButton = findViewById(R.id.playPauseButton)
        seekBar = findViewById(R.id.seekBar)
        currentTimeTextView = findViewById(R.id.currentTimeTextView)
        durationTextView = findViewById(R.id.durationTextView)

        seekBar.max = mediaPlayer.duration
        durationTextView.text = formatDuration(mediaPlayer.duration.toLong())

        playPauseButton.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.pause()
                playPauseButton.setImageResource(R.drawable.ic_launcher_background)//ic_play)
                isPlaying = false
            } else {
                mediaPlayer.start()
                playPauseButton.setImageResource(R.drawable.ic_launcher_background)//ic_pause)
                isPlaying = true
                updateSeekBar()
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not implemented
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not implemented
            }
        })

        mediaPlayer.setOnCompletionListener {
            playPauseButton.setImageResource(R.drawable.ic_launcher_background)//ic_play)
            isPlaying = false
            mediaPlayer.seekTo(0)
            seekBar.progress = 0
        }

        val fastForwardButton: ImageButton = findViewById(R.id.fastForwardButton)
        fastForwardButton.setOnClickListener{ fastForward()}
        val rewindButton: ImageButton = findViewById(R.id.rewindButton)
        rewindButton.setOnClickListener{ rewind()}
    }

    private fun updateSeekBar() {
        seekBar.progress = mediaPlayer.currentPosition
        currentTimeTextView.text = formatDuration(mediaPlayer.currentPosition.toLong())
        handler.postDelayed(updateSeekBarTask, 1000)
    }

    private val updateSeekBarTask = object : Runnable {
        override fun run() {
            updateSeekBar()
        }
    }

    private fun formatDuration(duration: Long): String {
        val minutes = duration / 1000 / 60
        val seconds = duration / 1000 % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(updateSeekBarTask)
    }

    private fun fastForward() {
        val currentPosition = mediaPlayer.currentPosition
        val newPosition = currentPosition + 10000 // 10 seconds in milliseconds

        if (newPosition <= mediaPlayer.duration) {
            mediaPlayer.seekTo(newPosition)
            seekBar.progress = newPosition
        } else {
            mediaPlayer.seekTo(mediaPlayer.duration)
            seekBar.progress = mediaPlayer.duration
        }
    }

    private fun rewind() {
        val currentPosition = mediaPlayer.currentPosition
        val newPosition = currentPosition - 10000 // 10 seconds in milliseconds

        if (newPosition >= 0) {
            mediaPlayer.seekTo(newPosition)
            seekBar.progress = newPosition
        } else {
            mediaPlayer.seekTo(0)
            seekBar.progress = 0
        }
    }
}
