package com.dom.lattsvenska.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Word::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {

        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "latt-svenska")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}