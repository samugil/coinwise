package com.app.coinwise.repository

import com.app.coinwise.data.local.Table
import retrofit2.http.GET

interface ServiceInterface {
    @GET("charts/transactions-per-second?timespan=5weeks&rollingAverage=8hours&format=json")
    suspend fun bitcoin(): Table
}