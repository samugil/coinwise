package com.app.coinwise.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("charts/market-price")
    suspend fun getChartItemsApi1year(@Query ("timespan") timespan: String): Response<ItemDto>

}