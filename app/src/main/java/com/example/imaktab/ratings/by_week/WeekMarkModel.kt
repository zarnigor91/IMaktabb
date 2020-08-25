package com.example.imaktab.home_work.passed
import com.example.imaktab.ratings.by_week.MarkModel
import com.google.gson.annotations.SerializedName

data class WeekMarkModel(
    @SerializedName("Monday")
    val monday:List<MarkModel>,
    @SerializedName("Tuesday")
    val tuesday:List<MarkModel>,
    @SerializedName("Wednesday")
    val wednesday:List<MarkModel>,
    @SerializedName("Thursday")
    val thursday:List<MarkModel>,
    @SerializedName("Friday")
    val friday:List<MarkModel>,
    @SerializedName("Saturday")
    val saturday:List<MarkModel>
)