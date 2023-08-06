package com.app.coinwise

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
//class GraficoViewModel(private val serviceInterface: ServiceInterface): ViewModel() {
//
//    private val _bitcoinLiveData = MutableLiveData<List<ListDTO>>()
//
//    val bitcoinLiveData: LiveData<List<ListDTO>> = _bitcoinLiveData
//
//    init {
//        getBitcoinList()
//    }
//
//    private fun getBitcoinList(){
//        viewModelScope.launch {
//            try {
//                val bitcoinList = serviceInterface.bitcoin().values
//                _bitcoinLiveData.value = bitcoinList
//            }
//            catch (ex:Exception){
//                ex.printStackTrace()
//            }
//        }
//    }
//
//    companion object{
//        fun create():GraficoViewModel {
//            val bitcoinService = RetrofitModule.createService()
//            return GraficoViewModel(bitcoinService)
//        }
//    }
//}