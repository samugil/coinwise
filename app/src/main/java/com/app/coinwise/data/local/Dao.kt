package com.app.coinwise.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {

    @Query("SELECT * FROM 'Table'")
    fun getAll(): List<Table>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(table: Table)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(table: Table)

    @Query("DELETE FROM 'Table' where id=:id")
    fun delete(id: Int)

}