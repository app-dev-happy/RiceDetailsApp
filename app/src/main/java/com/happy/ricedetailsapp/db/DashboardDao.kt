package com.happy.ricedetailsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.utility.AppConstant

@Dao
interface DashboardDao {
    @Query("select dashboardData from DashboardEntity where id = :id")
    fun getDashboardData(id: String = AppConstant.DashboardFileName): LiveData<DashBoardMainPojo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDashboardData(dashBoardMainPojo:DashboardEntity)

    @Delete
    fun deleteDashboardData(dashBoardMainPojo:DashboardEntity)

    @Query("DELETE FROM DashboardEntity")
    fun clearAll()
}