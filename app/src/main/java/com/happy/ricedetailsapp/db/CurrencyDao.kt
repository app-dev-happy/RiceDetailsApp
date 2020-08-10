package com.happy.ricedetailsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.happy.ricedetailsapp.utility.AppConstant

@Dao
interface CurrencyDao {
    @Query("select currencyData from CurrencyEntity where id = :id")
    fun getCurrencyData(id:String): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyData(currencyMap:CurrencyEntity)

    @Delete
    fun deleteCurrencyData(currencyMap:CurrencyEntity)

    @Query("DELETE FROM CurrencyEntity")
    fun clearAll()
}