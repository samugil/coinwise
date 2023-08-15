package com.app.coinwise

import android.app.Application
import androidx.room.Room
import com.app.coinwise.data.local.AppDataBase

class CoinWiseApplication: Application() {

    private lateinit var dataBase: AppDataBase
    override fun onCreate() {
        super.onCreate()

        dataBase = Room.databaseBuilder(
            applicationContext,AppDataBase::class.java, "coinwise-database"
        ).build()

    }
}