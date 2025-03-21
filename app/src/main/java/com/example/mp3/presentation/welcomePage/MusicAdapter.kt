package com.example.mp3.presentation.welcomePage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3.R
import java.util.Locale

class MusicAdapter(
    private val data: List<DataModel>,
    private val applicationContext: Context,
    private val welcomePage: WelcomePage
) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>()  {

    private var filteredMusicList: List<DataModel> = data


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.findViewById<TextView>(R.id.music_title)
        var duration = view.findViewById<TextView>(R.id.duration)
        var dis = view.findViewById<TextView>(R.id.music_location)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filteredMusicList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val positiondata=filteredMusicList[position]
        holder.title.text=positiondata.title
        val min=((positiondata.duration/1000)/60).toString()
        val sec = ((positiondata.duration/1000)%60).toString()
        val dur:String=min+"."+sec
        holder.duration.text=dur
        holder.dis.text=positiondata.artist.toString()
        holder.itemView.setOnClickListener {
            welcomePage.movetoMusicplayer(positiondata)
        }
    }

    fun filter(query: String) {
        val lowerCaseQuery = query.lowercase(Locale.getDefault())
        filteredMusicList = if (query.isBlank()) {
            data
        } else {
            data.filter { musicItem ->
                musicItem.title.lowercase(Locale.getDefault()).contains(lowerCaseQuery) ||
                        musicItem.artist.lowercase(Locale.getDefault())
                            .contains(lowerCaseQuery)
            }
        }
        notifyDataSetChanged()
    }


    /*companion object {
        fun filter(query: String,wholedata:List<DataModel>) {
            wholedata.filter { item ->



                // Implement your filtering logic here. For example, you can check if the item's title contains the query.
                item.title.contains(query, true)

            }


        }
    }*/

}
