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

    fun addPlaylist(playlist: PlaylistEntity) {
        viewModelScope.launch {
            addPlaylistUseCase.invoke(playlist)
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