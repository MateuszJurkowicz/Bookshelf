package com.example.marsphotos.fake

import com.example.bookshelf.data.NetworkBooksRepository
import com.example.bookshelf.fake.FakeBooksApiService
import com.example.bookshelf.fake.FakeDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBooksRepositoryTest {
    @Test
    fun networkBooksRepository_getBooks_verifyBooksList() =
        runTest {
            val repository = NetworkBooksRepository(
                booksApiService = FakeBooksApiService()
            )
            assertEquals(FakeDataSource.booksList.items, repository.getBooks("Harry Potter"))
        }
}