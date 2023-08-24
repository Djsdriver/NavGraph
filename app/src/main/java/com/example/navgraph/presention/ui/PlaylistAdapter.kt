package com.example.navgraph.presention.ui

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.navgraph.R
import com.example.navgraph.data.db.PlaylistEntity
import com.example.navgraph.databinding.ItemPlaylistBinding
import org.junit.runner.manipulation.Ordering.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistAdapter(): RecyclerView.Adapter<PlaylistViewHolder>() {
    var tracks = ArrayList<PlaylistEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    fun setTrackList(list: List<PlaylistEntity>) {
        tracks.clear()
        tracks.addAll(list)
        notifyDataSetChanged()
    }
}
class PlaylistViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val filePath = File(view.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_album")
    private val title: TextView = view.findViewById(R.id.tv_title)
    private val description: TextView = view.findViewById(R.id.tv_tracks_quantity)
    private val imagePlaylist: ImageView = view.findViewById(R.id.iv_playlist_big_image)

    fun bind(playlistEntity: PlaylistEntity) {
        title.text = playlistEntity.name
        description.text = playlistEntity.description

        val imageFile = File(filePath, playlistEntity.imagePath)
        Glide.with(itemView)
            .load(imageFile)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imagePlaylist)
    }
}