package com.thrq.bitebox.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FoodModel::class], version = 1)
abstract class AppDatabases : RoomDatabase() {

    companion object{
        private var database : AppDatabases? = null
        private val DATABASE_NAME = "bitebox"

        @Synchronized
        fun getInstance (context: Context): AppDatabases{
            if (database == null){
                database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabases::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
        return database!!
        }
    }

    abstract fun foodDao() : FoodDao
}