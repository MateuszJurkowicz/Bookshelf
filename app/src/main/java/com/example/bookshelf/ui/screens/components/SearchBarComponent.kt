package com.example.bookshelf.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.R
import com.example.bookshelf.ui.BooksApp
import com.example.bookshelf.ui.screens.home.BooksViewModel
import com.example.bookshelf.ui.theme.BookshelfTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(booksViewModel: BooksViewModel) {
    val isSearching by booksViewModel.isSearching.collectAsState()
    val searchText by booksViewModel.searchText.collectAsState()
    val historyItems = remember { mutableStateListOf("Harry Potter", "Lord of the Rings") }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        query = searchText,
        onQueryChange = booksViewModel::onSearchTextChange,
        onSearch = {
            if (searchText.isNotEmpty()) {
                historyItems.add(0, searchText)
                booksViewModel.getBooks(searchText)
                booksViewModel.onToggleSearch()
            } else {
                booksViewModel.onToggleSearch()
            }
        },
        active = isSearching,
        onActiveChange = { booksViewModel.onToggleSearch() },
        placeholder = { Text(text = stringResource(R.string.search)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search icon") },
        trailingIcon = {
            if (isSearching) {
                Icon(
                    modifier = Modifier.clickable {
                        if (searchText.isNotEmpty()) {
                            booksViewModel.onSearchTextChange("")
                        } else {
                            booksViewModel.onToggleSearch()
                        }
                    },
                    imageVector = Icons.Default.Close, contentDescription = "Close icon"
                )
            }
        },
    ) {
        if (isSearching) {
            historyItems.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            booksViewModel.onSearchTextChange(it)
                            booksViewModel.getBooks(it)
                        }
                ) {
                    Spacer(modifier = Modifier.width(40.dp))
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically),
                        imageVector = Icons.Default.History,
                        contentDescription = "History icon"
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically),
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}