package com.app.coinwise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private val retrofitModule = RetrofitModule
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn_clique)

        val newService = retrofitModule.createService()

        btn.setOnClickListener {

            newService
                .bitcoin()
                .enqueue(
                    object: Callback<BitcoinResponse> {
                        override fun onResponse(
                            call: Call<BitcoinResponse>,
                            response: retrofit2.Response<BitcoinResponse>
                        ) {
                            if (response.isSuccessful){
                                val newsResponse = response.body()!!
                                val newList = newsResponse.values

                                println(newList)
                            }
                        }

                        override fun onFailure(call: Call<BitcoinResponse>, t: Throwable) {
                            t.printStackTrace()
                        }

                    }
                )
        }
    }
}