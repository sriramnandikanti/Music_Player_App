package com.example.mp3.presentation.welcomePage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3.R
import com.example.mp3.presentation.savedPlayList.SavedPlayList

class SavedPageAdapter(
    private val data: List<DataModel>,
    private val applicationContext: Context,
    private val savedPlayList: SavedPlayList
): RecyclerView.Adapter<SavedPageAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.findViewById<TextView>(R.id.save_music_title)
        var duration = view.findViewById<TextView>(R.id.save_duration)
        var dis = view.findViewById<TextView>(R.id.save_music_location)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.savings_card_layout, parent, false)
        return SavedPageAdapter.ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val positiondata=data[position]
        holder.title.text=positiondata.title
        val min=((positiondata.duration/1000)/60).toString()
        val sec = ((positiondata.duration/1000)%60).toString()
        val dur:String=min+"."+sec
        holder.duration.text=dur
        holder.dis.text=positiondata.artist.toString()
        holder.itemView.setOnClickListener {
            savedPlayList.movetoMusicplayer(positiondata)
        }
    }
}