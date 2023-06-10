package com.rajai.deloitte.ui.home.dashboard

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DashBoardResponseModel(
    @SerializedName("status") val status: String?,
    @SerializedName("copyright") val copyright: String?,
    @SerializedName("num_results") val num_results: Int?,
    @SerializedName("results") val results: List<Results>
) : Parcelable

@Parcelize
data class MediaMetadata(
    @SerializedName("url") val url: String?,
    @SerializedName("format") val format: String?,
    @SerializedName("height") val height: Int?,
    @SerializedName("width") val width: Int?
) : Parcelable

@Parcelize
data class Media(
    @SerializedName("type") val type: String?,
    @SerializedName("subtype") val subtype: String?,
    @SerializedName("caption") val caption: String?,
    @SerializedName("copyright") val copyright: String?,
    @SerializedName("approved_for_syndication") val approved_for_syndication: Int?,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetadata?>?
) : Parcelable

@Parcelize
data class Results(
    @SerializedName("uri") val uri: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("id") val id: Double?,
    @SerializedName("asset_id") val asset_id: Double?,
    @SerializedName("source") val source: String?,
    @SerializedName("published_date") val published_date: String?,
    @SerializedName("updated") val updated: String?,
    @SerializedName("section") val section: String?,
    @SerializedName("subsection") val subsection: String?,
    @SerializedName("nytdsection") val nytdsection: String?,
    @SerializedName("adx_keywords") val adx_adx_keywordswords: String?,
    @SerializedName("column") val column: String?,
    @SerializedName("byline") val byline: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("abstract") val abstract: String?,
    @SerializedName("des_facet") val des_facet: List<String>?,
    @SerializedName("org_facet") val org_facet: List<String>?,
    @SerializedName("per_facet") val per_facet: List<String>?,
    @SerializedName("geo_facet") val geo_facet: List<String>?,
    @SerializedName("media") val media: List<Media?>?,
    @SerializedName("eta_id") val eta_id: Int?
) : Parcelable