package com.example.navgraph.domain.models

import androidx.room.PrimaryKey

data class PlaylistModel(
    val id: Int = 0,
val name: String,
val description: String,
val imagePath: String,
val trackCount: Int
)
