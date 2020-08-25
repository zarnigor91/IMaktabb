package com.example.imaktab.dashboard


import com.google.gson.annotations.SerializedName

data class DashboardResponce(
    @SerializedName("behaviors")
    val behaviors: Int? = 0,
    @SerializedName("complaints")
    val complaints: Int? = 0,
    @SerializedName("passes")
    val passes: Int? = 0,
    @SerializedName("praises")
    val praises: Int? = 0
)