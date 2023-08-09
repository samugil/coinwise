package com.app.coinwise.repository

import retrofit2.http.GET

interface ServiceInterface {
    @GET("charts/market-price")
    suspend fun bitcoin(): BitcoinResponse
}