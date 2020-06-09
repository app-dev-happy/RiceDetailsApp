package com.happy.ricedetailsapp.pojo

import java.io.Serializable

data class DashBoardMainPojo(
    val CurrencyContent: ArrayList<CurrencyContent>,
    val DashboardMainContent: ArrayList<DashboardMainContent>,
    val SeaPortContent: ArrayList<SeaPortContent>,
    val ClearancePortContent: ArrayList<SeaPortContent>
):Serializable