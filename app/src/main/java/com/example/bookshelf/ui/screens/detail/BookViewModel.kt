package com.example.bookshelf.ui.screens.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BooksApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.screens.home.BooksUiState
import com.example.bookshelf.ui.screens.home.BooksViewModel
import kotlinx.coroutines.launch

sealed interface BookUiState {
    data class Success(val book: Book) : BookUiState
    object Error : BookUiState
    object Loading : BookUiState
}

class BookViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    var bookUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set

    init {
        getBook()
    }

    fun getBook(id: String = "1") {
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try {
                val book = booksRepository.getBook(id)
                Log.d("BookViewModel", "Book $id fetched successfully: $book")
                BookUiState.Success(book!!)
            } catch (e: Exception) {
                BookUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                BookViewModel(booksRepository = booksRepository)
            }
        }
    }

}