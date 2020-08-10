package com.happy.ricedetailsapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.happy.ricedetailsapp.pojo.DashBoardMainPojo
import com.happy.ricedetailsapp.pojo.DashboardMainContent
import java.io.Serializable

@Entity
data class DashboardEntity(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "dashboardData") var dashBoardMainPojo: String
):Serializable

