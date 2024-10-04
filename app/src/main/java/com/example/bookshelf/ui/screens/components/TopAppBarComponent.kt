package com.example.bookshelf.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.BooksApp
import com.example.bookshelf.ui.screens.home.BooksViewModel
import com.example.bookshelf.ui.theme.BookshelfTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    currentScreen: BooksApp,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior,
    booksViewModel: BooksViewModel
) {
    var showSearchBar by remember { mutableStateOf(false) }

    Column {
        CenterAlignedTopAppBar(
            title = { Text(text = currentScreen.name) },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            },
            actions = {
                if (currentScreen == BooksApp.Home) {
                    IconButton(onClick = { showSearchBar = !showSearchBar }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior
        )
        // Conditionally display SearchBarComponent
        if (showSearchBar && currentScreen == BooksApp.Home) {
            SearchBarComponent(booksViewModel = booksViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    BookshelfTheme {
        TopAppBarComponent(
            BooksApp.Home,
            true,
            {},
            TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
            viewModel() // Pass a ViewModel instance
        )
    }
}