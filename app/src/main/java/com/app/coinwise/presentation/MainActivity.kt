package com.app.coinwise.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.app.coinwise.GraficoViewModel
import com.app.coinwise.R
import com.app.coinwise.repository.ListDTO

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GraficoViewModel
    private lateinit var img404: ImageView
    private lateinit var img500: ImageView
    private lateinit var img333: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img404 = findViewById(R.id.img_404)
        img500 = findViewById(R.id.img_500)
        img333 = findViewById(R.id.img_333)

        viewModel = GraficoViewModel.create()

        viewModel.bitcoinLiveData.observe(this){ bitcoinListDTO->
            val bitcoinList = bitcoinListDTO.map {
                ListDTO(x = it.x, y = it.y)
            }
        }

        viewModel.errorLiveData.observe(this){ errorMsg ->
            Log.d("ErrorData", "LiveData observed with value: $errorMsg")
            when (errorMsg){
                404 -> {
                    img404.visibility = View.VISIBLE
                    Toast.makeText(this, "Pagina não encontrada", Toast.LENGTH_LONG).show()
                }
                500 -> {
                    img500.visibility = View.VISIBLE
                    Toast.makeText(this, "Erro na conexão", Toast.LENGTH_LONG).show()
                }
                333 ->{
                    img333.visibility = View.VISIBLE
                    Toast.makeText(this, "A pagina está vazia", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}