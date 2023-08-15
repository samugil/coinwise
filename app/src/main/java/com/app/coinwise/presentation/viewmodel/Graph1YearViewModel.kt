package com.app.coinwise.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.coinwise.data.local.AppDataBase
import com.app.coinwise.data.local.Table
import com.app.coinwise.data.remote.RetrofitModule
import com.app.coinwise.repository.CoinWiseRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Graph1YearViewModel(private val repository: CoinWiseRepository) : ViewModel() {

    val chartItem: LiveData<Table> = repository.chartItem

    private val _errorLiveData = MutableLiveData<Int>()

    init {
        refreshChartItem()
    }

    fun refreshChartItem() {
        viewModelScope.launch {
            try {
                val response = repository.getChartItems()
                repository.refreshChartItems()
                if (!response.isSuccessful) {
                    _errorLiveData.postValue(response.code())

                }
            } catch (ex: Exception) {
                _errorLiveData.postValue(500) // Erro genérico de conexão
                Log.e("TAGY", "Exception: ${ex.message}")
            }
        }
    }

    // Criando função para converter a data vinda da API para o modelo DD/MM/AAAA.
    fun convertUnixTimestampToDateFormat(unixTimestamp: Int): String {
        val date = Date(unixTimestamp.toLong() * 1000)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }

    companion object{
        fun create(application: Application): Graph1YearViewModel {
            val bitcoinService = RetrofitModule.createService()
            val coinWiseDao = AppDataBase.getInstance(application).Dao()
            val repository = CoinWiseRepository(coinWiseDao, bitcoinService)

            return Graph1YearViewModel(repository)
        }
    }
}