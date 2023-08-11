package com.app.coinwise.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.coinwise.data.local.AppDataBase
import com.app.coinwise.data.local.Table
import com.app.coinwise.repository.CoinWiseRepository
import com.app.coinwise.data.remote.RetrofitModule
import kotlinx.coroutines.launch



class GraficoViewModel(private val repository: CoinWiseRepository): ViewModel() {


    val chartItem: LiveData<Table> = repository.chartItem


    private val _errorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int> = _errorLiveData



    init {
        refreshChartItem()
    }



    // Aqui está chamando o repository
    // Pegando o valor que veio dentro da função de dentro do repository
//    private fun refreshChartItem(){
//        viewModelScope.launch {
//            try {
//                repository.refreshChartItems()
//            } catch (ex: Exception){
//                Log.e("TAGY", "Exception: ${ex.message}")
//            }
//        }
//    }


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

    companion object{
        fun create(application: Application, ): GraficoViewModel {
            val bitcoinService = RetrofitModule.createService()
            val coinWiseDao = AppDataBase.getInstance(application).Dao()
            val repository = CoinWiseRepository(coinWiseDao, bitcoinService)

            return GraficoViewModel(repository)
        }
    }
}