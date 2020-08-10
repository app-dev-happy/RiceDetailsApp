package com.happy.ricedetailsapp.pojo

import java.io.Serializable

data class DashBoardMainPojo(
    val currencyContent: ArrayList<CurrencyContent>,
    val dashboardMainContent: ArrayList<DashboardMainContent>,
    val seaPortContent: ArrayList<SeaPortContent>,
    val clearancePortContent: ArrayList<SeaPortContent>,
    val detailsContent: ArrayList<DetailsContent>
):Serializable