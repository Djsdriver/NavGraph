package com.example.navgraph

import com.example.navgraph.data.db.PlaylistEntity
import com.example.navgraph.domain.models.PlaylistModel

fun PlaylistModel.toPlaylistEntity(): PlaylistEntity {
    return PlaylistEntity(
        id= this.id,
        name= this.name,
        description= this.description,
        imagePath= this.imagePath,
        trackCount = this.trackCount
    )
}

fun PlaylistEntity.toPlaylistModel(): PlaylistModel {
    return PlaylistModel(
        id= this.id,
        name= this.name,
        description= this.description,
        imagePath= this.imagePath,
        trackCount = this.trackCount
    )
}

