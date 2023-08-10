package com.app.coinwise.data.remote

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModule {

    fun createService(): Service {
        val logging = HttpLoggingInterceptor()
        logging.apply {
            HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit
            .Builder()
            .client(client)
            .baseUrl("https://api.blockchain.info/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))

        return retrofit.build().create(Service::class.java)
    }
}