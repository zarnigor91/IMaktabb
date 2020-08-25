package com.example.imaktab.home_work.confirms


import com.example.imaktab.home_work.Homework
import com.google.gson.annotations.SerializedName

data class ConfirmsResponce(
    @SerializedName("confirm")
    val confirm: Boolean? = false,
    @SerializedName("homework")
    val homework: Homework? = Homework(),
    @SerializedName("parent")
    val parent: String? = "",
    @SerializedName("pupil")
    val pupil: String? = ""
)