package com.dungle.android.aiactivassignment.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksResponse(
    @SerializedName("article")
    val article: String?,
    @SerializedName("flickr")
    val flickr: FlickrResponse,
    @SerializedName("patch")
    val patch: PatchResponse,
    @SerializedName("presskit")
    val presskit: String?,
    @SerializedName("reddit")
    val reddit: RedditResponse,
    @SerializedName("webcast")
    val webcast: String?,
    @SerializedName("wikipedia")
    val wikipedia: String?,
    @SerializedName("youtube_id")
    val youtubeId: String?
) : Parcelable