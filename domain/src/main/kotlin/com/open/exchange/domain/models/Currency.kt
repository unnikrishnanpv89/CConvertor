package com.open.exchange.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    @PrimaryKey
    val currencyCode: String,
    val currencyName: String,
    val isSelected: Boolean,
    val convertRate: Float = -1F
)