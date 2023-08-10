package com.app.coinwise.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Table(
    @PrimaryKey val id: Int = 1,
    val description: String,
    val name: String,
    val period: String,
    val status: String,
    val unit: String,
    val values: List<Value>

)