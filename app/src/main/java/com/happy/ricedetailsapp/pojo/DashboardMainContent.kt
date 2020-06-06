package com.happy.ricedetailsapp.pojo

import java.io.Serializable

data class DashboardMainContent(
    val appVersion: String,
    val iconURL: String,
    val subTitle: String,
    val title: String,
    val varietyItems: ArrayList<VarietyItem>,
    val visibility: Int
):Serializable