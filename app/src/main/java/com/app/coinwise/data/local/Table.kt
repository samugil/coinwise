package com.app.coinwise.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.coinwise.repository.ListDTO
import java.io.Serializable

@Entity
data class Table(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<ListDTO>
    ): Serializable



