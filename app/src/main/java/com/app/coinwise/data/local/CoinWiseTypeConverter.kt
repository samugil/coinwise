package com.app.coinwise.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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