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


class GraficoViewModel(private val repository: CoinWiseRepository): ViewModel() {

    private val _table= MutableLiveData<List<Table>>()
    val table: LiveData<List<Table>> = _table
    private val _bitcoinLiveData = MutableLiveData<List<Value>>()
    val bitcoinLiveData: LiveData<List<Value>> get() =  _bitcoinLiveData

    private val _errorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int> = _errorLiveData

    //    // LiveData to hold the entire Bitcoin data
    private val _bitcoinData = MutableLiveData<Table?>()
    private val bitcoinData: LiveData<Table?> get() = _bitcoinData

    init {
        getBitcoinList()
    }

    // Aqui está chamando o repository
    // Pegando o valor que veio dentro da função de dentro do repository
    fun getBitcoinList(){
        viewModelScope.launch {
            try {
                val bitcoin = repository.bitcoinData()
                _bitcoinData.value = bitcoin
                _bitcoinLiveData.value = bitcoin?.values ?: emptyList()
            } catch (ex: Exception){
                Log.e("TAGY", "Exception: ${ex.message}")
            }
        }
    }
    companion object{
        fun create(application: Application, ):GraficoViewModel {
            val bitcoinService = RetrofitModule.createService()
            val coinWiseDao = AppDataBase.getInstance(application).Dao()
            val valueDao = AppDataBase.getInstance(application).DaoValue()
            val repository:CoinWiseRepository = CoinWiseRepository(coinWiseDao, valueDao, bitcoinService)

            return GraficoViewModel(repository)
        }
    }
}