package com.example.navgraph.presention.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navgraph.data.db.PlaylistEntity
import com.example.navgraph.domain.usecase.GetAllPlaylistToListUseCase
import com.example.navgraph.domain.usecase.InsertPlayListToDatabaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val insertPlaylistToDatabaseUseCase: InsertPlayListToDatabaseUseCase,
    private val getAllPlaylistToListUseCase: GetAllPlaylistToListUseCase
    ) : ViewModel() {
    private val _playlistAdded = MutableLiveData<Boolean>()
    val playlistAdded: LiveData<Boolean> get() = _playlistAdded

    private val _state = MutableStateFlow<PlaylistState>(PlaylistState.Empty)
    val state: StateFlow<PlaylistState> = _state

    fun insertPlaylistToDatabase(playlist: PlaylistEntity) {
        viewModelScope.launch {
            insertPlaylistToDatabaseUseCase.invoke(playlist)
        }
    }




    fun getAllPlaylist() {
        viewModelScope.launch {
            getAllPlaylistToListUseCase.getAllTracks().collect { tracks ->
                if (tracks.isNotEmpty()) {
                    _state.value=PlaylistState.PlaylistLoaded(tracks)
                } else {
                    _state.value=PlaylistState.Empty
                }
            }
        }

    }

//    fun generateImageNameForStorage(): String {
//        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
//        return (1..5)
//            .map { allowedChars.random() }
//            .joinToString("")
//            .plus(".jpg")
//    }

    fun generateImageNameForStorage(): String {
       return "cover_${System.currentTimeMillis()}.jpg"
    }

}

sealed class PlaylistState {
    object Empty : PlaylistState()
    data class PlaylistLoaded(val tracks: List<PlaylistEntity>) : PlaylistState()
}