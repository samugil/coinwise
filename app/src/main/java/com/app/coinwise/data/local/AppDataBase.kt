package com.app.coinwise.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/*Criando a base de dados, a versão da base de dados foi
alterada devido alterações realizdas no formato e nomes
das tabelas e da própria base
 */
@Database(entities = [Table::class], version = 5)
@TypeConverters(CoinWiseTypeConverter::class)
abstract class AppDataBase : RoomDatabase(){

    abstract fun Dao(): Dao

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