package com.example.bookshelf.fake

import com.example.bookshelf.model.QueryResponse
import com.example.bookshelf.network.BooksApiService
import retrofit2.Response

class FakeBooksApiService : BooksApiService {
    override suspend fun getBooks(query: String): Response<QueryResponse> {
        return Response.success(FakeDataSource.booksList)
    }
}