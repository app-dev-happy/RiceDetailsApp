package com.happy.ricedetailsapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class CurrencyEntity(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "currencyData") var currencyMap: String
):Serializable