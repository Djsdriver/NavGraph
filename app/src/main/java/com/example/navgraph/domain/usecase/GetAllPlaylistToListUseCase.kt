package com.example.navgraph.domain.usecase

import com.example.navgraph.data.db.PlaylistEntity
import com.example.navgraph.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class GetAllPlaylistToListUseCase(private val playlistRepository: PlaylistRepository) {
    fun getAllTracks(): Flow<List<PlaylistEntity>> {
        return playlistRepository.getAllPlaylists()
    }
}