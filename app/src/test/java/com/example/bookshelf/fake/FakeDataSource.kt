package com.example.bookshelf.fake

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.ImageLinks
import com.example.bookshelf.model.QueryResponse
import com.example.bookshelf.model.VolumeInfo
import okhttp3.Response
import okhttp3.ResponseBody

object FakeDataSource {
    const val nameOne = "name1"
    const val nameTwo = "name2"
    const val typeOne = "type1"
    const val typeTwo = "type2"
    const val descriptionOne = "description1"
    const val descriptionTwo = "description2"
    const val imgOne = "url.1"
    const val imgTwo = "url.2"

    val booksList = QueryResponse(
        items = listOf(
            Book(
                id = "1",
                volumeInfo = VolumeInfo(
                    title = nameOne,
                    subtitle = typeOne,
                    description = descriptionOne,
                    authors = listOf("author1", "author2"),
                    publisher = "publisher1",
                    publishedDate = "2023",
                    imageLinks = ImageLinks(smallThumbnail = imgOne, thumbnail = imgOne)
                )
            ),
            Book(
                id = "2",
                volumeInfo = VolumeInfo(
                    title = nameTwo,
                    subtitle = typeTwo,
                    description = descriptionTwo,
                    authors = listOf("author3", "author4"),
                    publisher = "publisher2",
                    publishedDate = "2024",
                    imageLinks = ImageLinks(smallThumbnail = imgTwo, thumbnail = imgTwo)
                )
            )
    ),
        totalItems = 2,
        kind = "kind"
    )
}