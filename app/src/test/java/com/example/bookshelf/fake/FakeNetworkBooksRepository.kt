package com.example.bookshelf.fake

import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book

class FakeNetworkBooksRepository : BooksRepository {
    override suspend fun getBooks(query: String): List<Book> {
        return FakeDataSource.booksList.items
    }
}