package com.app.coinwise.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Table(

    @PrimaryKey
    val id: Int ): Serializable


