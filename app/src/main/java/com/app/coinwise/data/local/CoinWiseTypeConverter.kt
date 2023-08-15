package com.app.coinwise.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/* Criado para funcionar junto ao plugin Kotlin Data Class File From Json, onde o plugin criou
automaticamente da tabela Value e fez a conversão dos dados do Json para a tabela.*/
class CoinWiseTypeConverter {
    private val gson=Gson()

    @TypeConverter
    fun fromValueList(value: List<Value>):String{
        return gson.toJson(value)
    }

    @TypeConverter
    fun toValueList(value:String): List<Value>{
        val type=object : TypeToken<List<Value>>(){}.type
        return gson.fromJson(value,type)
   }
}