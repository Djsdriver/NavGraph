package com.example.navgraph.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database([PlaylistEntity::class], version = 1)
//@TypeConverters(ListConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getPlaylistDao(): PlaylistDao
}