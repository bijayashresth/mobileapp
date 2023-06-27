package com.exam.assignment.model

import com.google.gson.annotations.SerializedName

data class RelatedTopics(
    val Text: String? = null,
    val Result: String? = null,
    @SerializedName("FirstURL") val firstURL: String? = null,
    @SerializedName("Icon") val icon: Icon? = null
) : java.io.Serializable {
    fun getName(): String {
        return Text?.substringBefore("-") ?: "N/A"
    }

    data class Icon(
        val URL: String? = null
    ) : java.io.Serializable
}