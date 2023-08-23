package com.example.navgraph.data.repository

import com.example.navgraph.data.db.AppDatabase
import com.example.navgraph.data.db.PlaylistEntity
import com.example.navgraph.domain.models.PlaylistModel
import com.example.navgraph.domain.repository.PlaylistRepository
import com.example.navgraph.toPlaylistEntity
import kotlinx.coroutines.flow.Flow

class PlaylistRepositoryImpl(private val appDatabase: AppDatabase): PlaylistRepository {
    override fun getAllPlaylists(): Flow<List<PlaylistEntity>> {
        return appDatabase.getPlaylistDao().getAllPlaylists()
    }

    override suspend fun insertPlaylist(playlist: PlaylistEntity) {
        appDatabase.getPlaylistDao().insertPlaylist(playlist)
    }
}