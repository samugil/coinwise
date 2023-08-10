package com.app.coinwise.data.remote


data class ItemDto (
    val id: Int,
    val description: String,
    val name: String,
    val period: String,
    val status: String,
    val unit: String,
    val values: List<AxisDto>
)