package com.happy.ricedetailsapp.pojo

import java.io.Serializable

data class Packing(
    val appVersion: String,
    val kgsWeightItem: ArrayList<KgsWeightItem>,
    val lbsWeightItem: ArrayList<LbsWeightItem>,
    val subTitle: String,
    val title: String,
    val visibility: Int
): Serializable