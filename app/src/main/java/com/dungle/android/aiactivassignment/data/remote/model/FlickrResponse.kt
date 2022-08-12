package com.dungle.android.aiactivassignment.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlickrResponse(
    @SerializedName("original")
    val original: List<String>,
    @SerializedName("small")
    val small: List<String>
) : Parcelable