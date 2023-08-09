package com.app.coinwise

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.coinwise.data.local.AppDataBase
import com.app.coinwise.data.local.Dao
import com.app.coinwise.data.local.Table
import com.app.coinwise.data.local.Value
import com.app.coinwise.repository.CoinWiseRepository
import com.app.coinwise.repository.RetrofitModule
import com.app.coinwise.repository.ServiceInterface
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GraficoViewModel(private val serviceInterface: ServiceInterface, private var repository: CoinWiseRepository,
    private val dao:Dao): ViewModel() {

    private val _table= MutableLiveData<List<Table>>()
    val table: LiveData<List<Table>> = _table
    private val _bitcoinLiveData = MutableLiveData<List<Value>>()
    val bitcoinLiveData: LiveData<List<Value>> = _bitcoinLiveData

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
                    dao.insert(bitcoinList.values)
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

//    fun insertIntoDataBase() {
//        viewModelScope.launch {
//            dao. = getBitcoinList()
//            repository.saveList(_bitcoinLiveData.value)
//            _table.value = repository.getList()
//        }
//    }

    companion object{
        fun create(application: Application, ):GraficoViewModel {
            val bitcoinService = RetrofitModule.createService()
            val coinWiseDao = AppDataBase.getInstance(application).Dao()
            val repository:CoinWiseRepository = CoinWiseRepository(coinWiseDao)

            return GraficoViewModel(bitcoinService,repository, coinWiseDao)
        }
    }
}