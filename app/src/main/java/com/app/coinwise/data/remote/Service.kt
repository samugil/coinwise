package com.app.coinwise.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("charts/market-price")
    suspend fun getChartItemsApi(): Response<ItemDto>

}