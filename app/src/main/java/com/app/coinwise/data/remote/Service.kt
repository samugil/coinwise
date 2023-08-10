package com.app.coinwise.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("charts/transactions-per-second?timespan=5weeks&rollingAverage=8hours&format=json")
    suspend fun getChartItemsApi(): Response<ItemDto>

}