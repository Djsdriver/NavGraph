package com.example.navgraph.presention.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navgraph.data.db.PlaylistEntity
import com.example.navgraph.domain.usecase.GetAllPlayListUseCase
import kotlinx.coroutines.launch

class PlaylistViewModel(private val addPlaylistUseCase: GetAllPlayListUseCase) : ViewModel() {
    private val _playlistAdded = MutableLiveData<Boolean>()
    val playlistAdded: LiveData<Boolean> get() = _playlistAdded

    suspend fun addPlaylist(playlist: PlaylistEntity) {
            addPlaylistUseCase.invoke(playlist)


    }
}