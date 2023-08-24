package com.example.navgraph.domain.usecase

import com.example.navgraph.data.db.PlaylistEntity
import com.example.navgraph.domain.repository.PlaylistRepository

class InsertPlayListToDatabaseUseCase(private val playlistRepository: PlaylistRepository) {
    suspend fun invoke(playlist: PlaylistEntity) {
        playlistRepository.insertPlaylist(playlist)
    }
}