package com.app.coinwise.repository

import android.util.Log
import com.app.coinwise.data.local.Dao
import com.app.coinwise.data.local.DaoValue
import com.app.coinwise.data.local.Table
import com.app.coinwise.data.local.Value



class CoinWiseRepository (private val dao: Dao, private val valueDao: DaoValue, private val serviceInterface: ServiceInterface) {

//    suspend fun saveList (table: List<Value>?){
//        dao.insert(table)
//
//    }
//
//    suspend fun getList(): List<Table>{
//
//        return dao.getAll()
//
//    }

    // Essa função vai dentro do "Query" e pega a ultima atualização
    // Se for diferente de null, ela vai buscar on-line e salva no banco de dados
    // Se for null, vai mostrar o ultimo que foi salvo
    suspend fun bitcoinData(): Table? {
        try {
            val bitcoinOff = dao.getLastBitcoin()
            if (bitcoinOff != null) {
                return bitcoinOff
            } else {
                val response = serviceInterface.bitcoin()
                if (response.isSuccessful) {
                    val bitcoin = response.body()
                    bitcoin?.let {
                        priceBitcoin(bitcoin)
                    }
                    return bitcoin
                } else {
                    // Handle API call error if needed
                    Log.e("TAGY", "API call failed with code: ${response.code()}")
                }
            }

        } catch (ex: Exception) {
            // Handle other exceptions if needed
            Log.e("TAGY", "Exception: ${ex.message}")
        }

        return null
    }

    // Aqui estamos inserindo o valores da Table dentro do Dao
    // E o "ForEach" usamos para percorrer a table
    private suspend fun priceBitcoin(table: Table){
        dao.insert(table)
        table.values.forEach {
            valueDao.insertValue(it)
        }
    }
}