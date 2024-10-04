package com.example.bookshelf.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.R
import com.example.bookshelf.ui.screens.components.SearchBarComponent
import com.example.bookshelf.ui.screens.home.BooksViewModel
import com.example.bookshelf.ui.screens.home.HomeScreen
import com.example.bookshelf.ui.screens.components.TopAppBarComponent
import com.example.bookshelf.ui.screens.detail.BookViewModel
import com.example.bookshelf.ui.screens.detail.DetailScreen


enum class BooksApp(@StringRes val title: Int) {
    Home(title = R.string.home_screen),
    Detail(title = R.string.detail_screen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksApp(
    booksViewModel: BooksViewModel =
        viewModel(factory = BooksViewModel.Factory),
    bookViewModel: BookViewModel =
        viewModel(factory = BookViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BooksApp.valueOf(
        backStackEntry?.destination?.route ?: BooksApp.Home.name
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBarComponent(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    scrollBehavior = scrollBehavior,
                    booksViewModel = booksViewModel
                )
            }
        ) {

            NavHost(
                navController = navController,
                startDestination = BooksApp.Home.name,
                modifier = Modifier.padding(it)
            ) {
                composable(route = BooksApp.Home.name) {
                    HomeScreen(
                        booksUiState = booksViewModel.booksUiState,
                        retryAction = booksViewModel::getBooks,
                        modifier = Modifier.fillMaxSize(),
                        onDetailsClick = {
                            bookViewModel.getBook(it.id)
                            navController.navigate(BooksApp.Detail.name)
                        }
                    )
                }
                composable(route = BooksApp.Detail.name) {
                    DetailScreen(
                        modifier = Modifier.fillMaxSize(),
                        bookUiState = bookViewModel.bookUiState
                    )
                }
            }
        }
    }
}


