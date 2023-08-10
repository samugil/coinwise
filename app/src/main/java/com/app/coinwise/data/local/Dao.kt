package com.app.coinwise.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface Dao {

    @Query("SELECT * FROM 'table' ORDER BY id DESC LIMIT 1")
    fun getLastChartItem(): LiveData<Table>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bitcoin: Table)

//    @Query("SELECT * FROM 'Table'")
//    suspend fun getAll(): List<Table>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(table: List<Value>?)
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun update(table: Table)
//
//    @Query("DELETE FROM 'Table' where id=:id")
//    suspend fun delete(id: Int)

}