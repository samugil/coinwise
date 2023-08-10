package com.app.coinwise.data.local

import android.renderscript.Sampler
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DaoValue {

    // Igual a outra, mas so insere os valores da Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValue(value: Value)

}