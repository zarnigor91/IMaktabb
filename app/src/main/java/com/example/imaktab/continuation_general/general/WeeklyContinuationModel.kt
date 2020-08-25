package com.example.imaktab.continuation_general.general

import com.google.gson.annotations.SerializedName

data class WeeklyContinuationModel(
    @SerializedName("Monday")
    val monday:List<ContinuationModel>,
    @SerializedName("Tuesday")
    val tuesday:List<ContinuationModel>,
    @SerializedName("Wednesday")
    val wednesday:List<ContinuationModel>,
    @SerializedName("Thursday")
    val thursday:List<ContinuationModel>,
    @SerializedName("Friday")
    val friday:List<ContinuationModel>,
    @SerializedName("Saturday")
    val saturday:List<ContinuationModel>
)