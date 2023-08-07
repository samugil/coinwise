package com.app.coinwise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Table::class], version = 1)
abstract class AppDataBase : RoomDatabase(){

    abstract fun Dao(): Dao

}