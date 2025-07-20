package com.rajendra.dailypulsekmp.android.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rajendra.dailypulsekmp.android.ui.comman.ErrorContent
import com.rajendra.dailypulsekmp.android.ui.comman.LoadingIndicator
import com.rajendra.dailypulsekmp.domain.model.NewsApiResponse
import com.rajendra.dailypulsekmp.presentation.viewmodel.HomeViewModel
import com.rajendra.dailypulsekmp.presentation.viewmodel.NewsScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onEvent: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val screenState = homeViewModel.screenState.collectAsState().value
    LaunchedEffect(key1 = Unit) {
        homeViewModel.getNewsList() // Call your ViewModel function to fetch data
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home Screen") },
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                when (screenState) {
                     NewsScreenState.Loading -> {
                        LoadingIndicator()
                    }

                     is NewsScreenState.Success -> {
                        SuccessContent(screenState.articleList)
                    }

                     is NewsScreenState.Error -> {
                        ErrorContent(screenState.message)
                    }
                }
            }
        }
    )
}

@Composable
fun SuccessContent(articles: List<NewsApiResponse.Article?>, modifier: Modifier = Modifier) {
    if (articles.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                text = "No news articles found at the moment.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    } else {
        // Replace this with your actual UI for displaying articles, e.g., a LazyColumn
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // Example: Displaying first few article titles
            LazyColumn {
                items(articles) { it ->
                    ArticleCard(
                        article = it?:null,
                        onArticleClick = { clickedArticle ->
                            // Handle click, e.g., navigate to article detail screen
                            println("Clicked on: ${clickedArticle?.title}")
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCard(
    article: NewsApiResponse.Article?,
    modifier: Modifier = Modifier,
    onArticleClick: (NewsApiResponse.Article?) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        onClick = { onArticleClick(article) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            // Image (Optional)
            article?.urlToImage?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = imageUrl)
                            .apply(block = fun ImageRequest.Builder.() {
                                // Optional: Add crossfade, placeholder, error drawable
                                // placeholder(R.drawable.placeholder_image)
                                // error(R.drawable.error_image)
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = article?.title ?: "Article image", // Accessibility
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp) // Adjust height as needed
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop // Crop to fill bounds
                )
            }

            // Content Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Title
                article?.title?.let { title ->
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Description (Optional)
                article?.description?.let { description ->
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Source and PublishedAt (Optional)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    article?.source?.name?.let { sourceName ->
                        Text(
                            text = sourceName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    article?.publishedAt?.let { publishedDate ->
                        // You might want to format this date
                        Text(
                            text = publishedDate, // Consider formatting: e.g., "3 hours ago" or "Jul 20, 2025"
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Author (Optional, less prominent)
                article?.author?.let { author ->
                    if (author.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "By $author",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}