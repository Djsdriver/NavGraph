package com.example.navgraph.domain.repository

import com.example.navgraph.data.db.PlaylistEntity
import com.example.navgraph.domain.models.PlaylistModel
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    fun getAllPlaylists(): Flow<List<PlaylistEntity>>

    suspend fun insertPlaylist(playlist: PlaylistEntity)
}