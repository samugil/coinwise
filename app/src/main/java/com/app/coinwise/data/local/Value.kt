package com.app.coinwise.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Value(
    @PrimaryKey val id: Int = 0,
    val x: Int,
    val y: Double
)