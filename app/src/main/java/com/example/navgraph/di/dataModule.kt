package com.example.navgraph.di

import androidx.room.Room
import com.example.navgraph.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "itunes_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}