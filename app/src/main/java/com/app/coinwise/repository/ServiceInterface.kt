package com.app.coinwise.repository

import com.app.coinwise.data.local.Table
import retrofit2.Response
import retrofit2.http.GET

interface ServiceInterface {
    @GET("charts/market-price")
    suspend fun bitcoin(): Response<Table>
}