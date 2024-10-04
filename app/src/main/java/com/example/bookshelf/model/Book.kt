package com.example.bookshelf.model

import androidx.core.text.HtmlCompat
import kotlinx.serialization.Serializable


@Serializable
data class Book(
    val id: String, val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String? = null,
    val description: String? = null,
    val imageLinks: ImageLinks? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null
) {
    val descriptionHtml: String
        get() = HtmlCompat.fromHtml(description.toString(), 0).toString()
}

@Serializable
data class ImageLinks(
    val thumbnail: String,
    val large: String? = null
) {
    val httpsThumbnail: String
        get() = thumbnail.replace("http", "https")
    val httpsLarge: String
        get() = large?.replace("http", "https") ?: httpsThumbnail

}