package com.app.coinwise.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.coinwise.repository.ListDTO
import java.io.Serializable

@Entity (tableName = "table")
data class Table(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<ListDTO>
    ): Serializable



