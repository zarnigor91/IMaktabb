package com.example.imaktab.continuation_general.sciences


import com.google.gson.annotations.SerializedName

data class Subjects(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = ""
)