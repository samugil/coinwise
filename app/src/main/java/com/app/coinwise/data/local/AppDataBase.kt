package com.app.coinwise.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [Table::class,Value::class], version = 1)
@TypeConverters(CoinWiseTypeConverter::class)
abstract class AppDataBase : RoomDatabase(){

    abstract fun Dao(): Dao
    // So estamos chamando a nova DaoValue
    abstract fun DaoValue(): DaoValue

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}