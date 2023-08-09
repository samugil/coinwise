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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class MainActivity : AppCompatActivity() {

    private lateinit var img404: ImageView
    private lateinit var img500: ImageView
    private lateinit var img333: ImageView
    private lateinit var lineChartBitcoin: LineChart

    private val viewModel: GraficoViewModel by lazy {
        GraficoViewModel.create(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineChartBitcoin = findViewById(R.id.line_chart_bitcoin)
        setUpLineCharts()

        img404 = findViewById(R.id.img_404)
        img500 = findViewById(R.id.img_500)
        img333 = findViewById(R.id.img_333)

        viewModel.bitcoinLiveData.observe(this){ bitcoinListDTO->
            val bitcoinList = bitcoinListDTO.map {
                ListDTO(x = it.x, y = it.y)
            }

            updateLineChart(bitcoinList)
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

    override fun onStart() {
        super.onStart()

    }

    private fun updateLineChart(bitcoinList: List<ListDTO>) {
        val entries = bitcoinList.mapIndexed { _, listDTO ->
            Entry(listDTO.x.toFloat(), listDTO.y)
        }
        val lineDataSet = LineDataSet(entries, "Bitcoin Price")
        lineDataSet.color = resources.getColor(R.color.green_500)
        lineDataSet.circleRadius = 1f
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = resources.getColor((R.color.green_500))
        lineDataSet.fillAlpha = 30

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)

        val lineData = LineData(dataSets)
        lineChartBitcoin.data = lineData
        lineChartBitcoin.invalidate()

    }

    private fun setUpLineCharts() {
        // 1 year chart
        lineChartBitcoin.setTouchEnabled(true)
        lineChartBitcoin.setPinchZoom(false)
        lineChartBitcoin.description = Description().apply { text = "Bitcoin Market Price" }
        lineChartBitcoin.setBackgroundColor(resources.getColor(R.color.white))
        lineChartBitcoin.animateXY(3000,3000)
    }
}