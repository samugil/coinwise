package com.app.coinwise

import retrofit2.Call
import retrofit2.http.GET

interface ServiceInterface {
//    charts/transactions-per-second?timespan=5weeks&rollingAverage=8hours&format=json
    @GET("charts/market-price")
    fun bitcoin(): Call<BitcoinResponse>
}