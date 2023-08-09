package com.app.coinwise.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {

    @Query("SELECT * FROM 'Table'")
    suspend fun getAll(): List<Table>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(table: List<Table>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(table: Table)

    @Query("DELETE FROM 'Table' where id=:id")
    suspend fun delete(id: Int)

}