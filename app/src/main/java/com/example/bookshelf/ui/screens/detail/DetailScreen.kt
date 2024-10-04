package com.example.bookshelf.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.VolumeInfo
import com.example.bookshelf.ui.screens.components.ErrorScreen
import com.example.bookshelf.ui.screens.components.LoadingScreen
import com.example.bookshelf.ui.theme.BookshelfTheme

@Composable
fun DetailScreen(
    modifier: Modifier, bookUiState: BookUiState
) {
    when (bookUiState) {
        is BookUiState.Loading -> LoadingScreen()
        is BookUiState.Success -> SimpleBookCard(
            book = bookUiState.book,
            modifier = modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
            contentPadding = 0.dp
        )

        else -> ErrorScreen({ /* TODO */ }, modifier)
    }
}

@Composable
fun SimpleBookCard(book: Book, modifier: Modifier = Modifier, contentPadding: Dp = 0.dp) {
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .shadow(8.dp)
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = book.volumeInfo.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(12.dp)
            )
            Text(
                text = book.volumeInfo.descriptionHtml ?: "", // Handle null description
                style = MaterialTheme.typography.titleMedium,
                maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable {
                    isDescriptionExpanded = !isDescriptionExpanded
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = ("Publisher: " + book.volumeInfo.publisher),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = ("Published date: " + book.volumeInfo.publishedDate),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = ("Authors: " + book.volumeInfo.authors),
                style = MaterialTheme.typography.bodyMedium,
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.httpsLarge)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = stringResource(id = R.string.book_photo),
                modifier = Modifier
                    .size(300.dp)
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    BookshelfTheme {
        val mockData =
            Book(
                id = "123",
                volumeInfo = VolumeInfo(
                    title = "A book",
                    description = "Caniss ortum, tanquam bassus exemplar.",
                    publishedDate = "11/11/2011",
                    authors = listOf("AAA", "aaa"),
                    publisher = "John Carter",
                    imageLinks = null,
                )
            )
        SimpleBookCard(
            book = mockData
        )
    }
}
