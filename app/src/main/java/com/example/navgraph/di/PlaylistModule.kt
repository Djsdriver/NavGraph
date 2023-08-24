package com.example.navgraph.di

import com.example.navgraph.data.repository.PlaylistRepositoryImpl
import com.example.navgraph.domain.repository.PlaylistRepository
import com.example.navgraph.domain.usecase.GetAllPlaylistToListUseCase
import com.example.navgraph.domain.usecase.InsertPlayListToDatabaseUseCase
import com.example.navgraph.presention.ui.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playlist = module {


    viewModel {
        PlaylistViewModel(get(),get())
    }

    single<PlaylistRepository> { PlaylistRepositoryImpl(get()) }


    factory { InsertPlayListToDatabaseUseCase (get()) }
    factory { GetAllPlaylistToListUseCase (get()) }




}