package com.example.navgraph.di

import androidx.room.Room
import com.example.navgraph.data.db.AppDatabase
import com.example.navgraph.data.repository.PlaylistRepositoryImpl
import com.example.navgraph.domain.repository.PlaylistRepository
import com.example.navgraph.domain.usecase.GetAllPlayListUseCase
import com.example.navgraph.presention.ui.PlaylistViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playlist = module {


    viewModel {
        PlaylistViewModel(get())
    }

    single<PlaylistRepository> { PlaylistRepositoryImpl(get()) }


    factory { GetAllPlayListUseCase (get()) }




}