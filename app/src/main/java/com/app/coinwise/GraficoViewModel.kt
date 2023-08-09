package com.app.coinwise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.coinwise.repository.ListDTO
import com.app.coinwise.repository.RetrofitModule
import com.app.coinwise.repository.ServiceInterface
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GraficoViewModel(private val serviceInterface: ServiceInterface): ViewModel() {

    private val _bitcoinLiveData = MutableLiveData<List<ListDTO>>()
    val bitcoinLiveData: LiveData<List<ListDTO>> = _bitcoinLiveData

    private val _errorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int> = _errorLiveData

    init {
        getBitcoinList()
    }

    private fun getBitcoinList(){
        viewModelScope.launch {
            try {
                val bitcoinList = serviceInterface.bitcoin()
                if (bitcoinList.values.isEmpty()){
                    _errorLiveData.value = 333
                } else {
                    _bitcoinLiveData.value = bitcoinList.values
                }
            }
            catch (ex:Exception){
                ex.printStackTrace()
                if (ex is HttpException) {
                    val errorCode = ex.code()
                    _errorLiveData.value = errorCode
                    Log.e("BitcoinData", "HTTP Error code: $errorCode")
                } else {
                    Log.e("BitcoinData", "Error fetching Bitcoin data: ${ex.message}")
                }
            }
        }
    }

    companion object{
        fun create():GraficoViewModel {
            val bitcoinService = RetrofitModule.createService()
            return GraficoViewModel(bitcoinService)
        }
    }
}