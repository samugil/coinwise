package com.app.coinwise.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.app.coinwise.GraficoViewModel
import com.app.coinwise.R
import com.app.coinwise.repository.ListDTO
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class MainActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private lateinit var img404: ImageView
    private lateinit var img500: ImageView
    private lateinit var img333: ImageView
    private lateinit var lineChartBitcoin: LineChart
    private lateinit var textViewData: TextView

    private val viewModel: GraficoViewModel by lazy {
        GraficoViewModel.create(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineChartBitcoin = findViewById(R.id.line_chart_bitcoin)
        setUpLineCharts()
        textViewData = findViewById(R.id.text_view_dados)

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
        lineDataSet.setDrawCircles(false)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.highLightColor = resources.getColor(R.color.black)
        lineDataSet.lineWidth = 0.5f

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)

        val lineData = LineData(dataSets)
        lineChartBitcoin.data = lineData

        val xAxis = lineChartBitcoin.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return viewModel.convertUnixTimestampToDateFormat(value.toInt())
            }
        }

        lineChartBitcoin.invalidate()

    }

    private fun setUpLineCharts() {
        // 1 year chart
        lineChartBitcoin.setTouchEnabled(true)
        lineChartBitcoin.setPinchZoom(false)
        lineChartBitcoin.setBackgroundColor(resources.getColor(R.color.white))
        lineChartBitcoin.animateXY(1200,1200)
        lineChartBitcoin.description.isEnabled = false
        lineChartBitcoin.isDragEnabled = false
        lineChartBitcoin.setScaleEnabled(false)
        lineChartBitcoin.isDoubleTapToZoomEnabled = false
        lineChartBitcoin.setPinchZoom(false)
        lineChartBitcoin.setOnChartValueSelectedListener(this)
        lineChartBitcoin.legend.isEnabled = false

        val xAxis = lineChartBitcoin.xAxis
        xAxis.textColor = resources.getColor(R.color.black)
        xAxis.textSize = 12f
        xAxis.labelCount = 3
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val yAxisLeft = lineChartBitcoin.axisLeft
        yAxisLeft.setDrawLabels(true)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.textColor = resources.getColor(R.color.black)
        yAxisLeft.textSize = 12f
        yAxisLeft.axisLineColor = resources.getColor(R.color.black)


        val yAxisRight = lineChartBitcoin.axisRight
        yAxisRight.isEnabled = false
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e != null) {
            val xValue = e.x.toInt()
            val yValue = e.y
            val xValueFormatted = viewModel.convertUnixTimestampToDateFormat(xValue)
            textViewData.text = "$xValueFormatted - Preço: $$yValue"

        }
    }

    override fun onNothingSelected() {
        textViewData.text = "Clique no gráfico para exibir os valores"
    }
}