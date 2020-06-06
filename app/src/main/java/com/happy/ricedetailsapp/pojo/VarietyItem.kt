package com.happy.ricedetailsapp.pojo

import java.io.Serializable

data class VarietyItem(
    val appVersion: String,
    val iconURL: String,
    val packing: List<Packing>,
    val stdPrice: Int,
    val subTitle: String,
    val title: String,
    val visibility: Int
): Serializable