package com.app.coinwise.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.coinwise.R
import com.app.coinwise.data.local.Value
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class MainActivity : AppCompatActivity() {

    private lateinit var swipeToRefresh: SwipeRefreshLayout
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


        swipeToRefresh = findViewById(R.id.swipeToRefresh)
        lineChartBitcoin = findViewById(R.id.line_chart_bitcoin)
        refreshApp()
        setUpLineCharts()

        img404 = findViewById(R.id.img_404)
        img500 = findViewById(R.id.img_500)
        img333 = findViewById(R.id.img_333)

        viewModel.errorLiveData.observe(this) { errorMsg ->
            img404.visibility = if (errorMsg == 404) View.VISIBLE else View.GONE
            if (errorMsg == 500) Toast.makeText(this, "Sem conexão de internet", Toast.LENGTH_LONG).show()
            img333.visibility = if (errorMsg == 333) View.VISIBLE else View.GONE

            val message = when (errorMsg) {
                404 -> "Pagina não encontrada"
                // está comentado porque aparece dois Toats, depois vemos qual deixamos
//                500 -> "Erro na conexão"
                333 -> "A pagina está vazia"
                else -> "Erro desconhecido"
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

    }


    override fun onStart() {
        super.onStart()

        if (isNetworkAvailable(this)) {
            viewModel.refreshChartItem()
        } else {
            viewModel.chartItem.observe(this) { chartItem ->
                updateLineChart(chartItem.values)
            }
        }
    }

    private fun refreshApp(){

        swipeToRefresh.setOnRefreshListener {

            isNetworkAvailable(this)

            Toast.makeText(this, "Pagina atualizada", Toast.LENGTH_SHORT).show()
            swipeToRefresh.isRefreshing = false
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            return networkCapabilities != null &&
                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }


    private fun updateLineChart(bitcoinList: List<Value>) {
        val entries = bitcoinList.mapIndexed { _, listDTO ->
            Entry(listDTO.x.toFloat(), listDTO.y.toFloat())
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
        lineChartBitcoin.animateXY(3000, 3000)
    }
}