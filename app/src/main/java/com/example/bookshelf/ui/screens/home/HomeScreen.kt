package com.example.bookshelf.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.ImageLinks
import com.example.bookshelf.model.VolumeInfo
import com.example.bookshelf.ui.screens.components.ErrorScreen
import com.example.bookshelf.ui.screens.components.LoadingScreen
import com.example.bookshelf.ui.theme.BookshelfTheme


@Composable
fun HomeScreen(
    booksUiState: BooksUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailsClick: (Book) -> Unit,
) {
    when (booksUiState) {
        is BooksUiState.Loading -> LoadingScreen()
        is BooksUiState.Success -> BooksGridScreen(
            books = booksUiState.books,
            modifier = modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
            onDetailsClick = onDetailsClick
        )

        else -> ErrorScreen(retryAction, modifier)
    }
}


@Composable
fun BookCard(book: Book, modifier: Modifier = Modifier, onDetailsClick: (Book) -> Unit) {
    Card(
        onClick = { onDetailsClick(book) },
        modifier = modifier
            .size(300.dp)
            .shadow(6.dp),

        shape = RoundedCornerShape(8.dp),

        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = book.volumeInfo.title.toString(),
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.httpsThumbnail)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = stringResource(id = R.string.book_photo),
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun BooksGridScreen(
    books: List<Book>,
    modifier: Modifier,
    onDetailsClick: (Book) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp)
    ) {
        items(items = books, key = { book -> book.id }
        ) { book ->
            BookCard(
                book = book,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxSize(),
                onDetailsClick = onDetailsClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BooksGridScreenPreview() {
    BookshelfTheme {
        val mockData = List(10) {
            Book(
                id = it.toString(),
                volumeInfo = VolumeInfo(
                    "title",
                    "description",
                    ImageLinks("smallThumbnail", "thumbnail"),
                    listOf("author1", "author2"),
                    "publisher",
                    "publishedDate"
                )
            )
        }
        BooksGridScreen(books = mockData, modifier = Modifier, onDetailsClick = {})
    }
}