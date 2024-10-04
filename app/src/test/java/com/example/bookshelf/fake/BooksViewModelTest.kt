package com.example.bookshelf.fake

import com.example.bookshelf.rules.TestDispatcherRule
import com.example.bookshelf.ui.screens.home.BooksUiState
import com.example.bookshelf.ui.screens.home.BooksViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BooksViewModelTest {
    @get: Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun bookViewModel_getBooks_verifyBookUiStateSuccess() =
        runTest {
            val booksViewModel = BooksViewModel(
                booksRepository = FakeNetworkBooksRepository()
            )
            booksViewModel.getBooks()
            assertEquals(
                BooksUiState.Success(FakeDataSource.booksList.items),
                booksViewModel.booksUiState
            )
        }
}