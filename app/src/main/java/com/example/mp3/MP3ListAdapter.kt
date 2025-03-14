package com.example.mp3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MP3ListAdapter(private val mp3Files: ArrayList<MP3File>,
                     private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<MP3ListAdapter.MP3ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MP3ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_mp3, parent, false)
        return MP3ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MP3ViewHolder, position: Int) {
        val mp3File = mp3Files[position]
        holder.titleTextView.text = mp3File.title
        holder.artistTextView.text = mp3File.artist
        holder.durationTextView.text = formatDuration(mp3File.duration)
        holder.optionsImageView.setOnClickListener {
            // Handle click event for the three dots icon
        }
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return mp3Files.size
    }

    class MP3ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        val durationTextView: TextView = itemView.findViewById(R.id.durationTextView)
        val optionsImageView: ImageView = itemView.findViewById(R.id.optionsImageView)
    }

    fun updateData(newData: List<MP3File>) {
        var mp3Files: List<MP3File> = emptyList()
        mp3Files = newData
        notifyDataSetChanged() // Notify RecyclerView that the data has changed
    }

    private fun formatDuration(duration: Int): String {
        val minutes = duration / 1000 / 60
        val seconds = duration / 1000 % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
