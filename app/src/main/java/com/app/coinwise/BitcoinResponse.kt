package com.app.coinwise

data class BitcoinResponse(
    val name: String,
    val unit: String,
    val values: List<ListDTO>
)

data class ListDTO(
    val x: Int,
    val y: Float
)
