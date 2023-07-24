package com.example.search.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.search.R
import com.example.search.ui.contract.SearchDirectoryContract
import com.example.search.ui.contract.SearchDirectoryContract.SearchDirectoryViewState
import com.example.search.ui.theme.Black200
import com.example.search.ui.theme.Black700
import com.example.search.ui.theme.SearchTheme
import com.example.search.ui.theme.White200
import com.example.search.viewmodel.SearchDirectoryViewModel

@Composable
fun SearchDirectory(
    viewModel: SearchDirectoryViewModel,
    onSearchLabelClick: () -> Unit,
) {
    when (val state = viewModel.viewState.collectAsStateWithLifecycle().value) {
        SearchDirectoryViewState.Loading -> LoadingScreen()
        is SearchDirectoryViewState.Success -> SuccessScreen(onSearchLabelClick = {
            viewModel.onIntention(
                SearchDirectoryContract.SearchDirectoryIntention.SearchLabelIntention
            )
        })
        SearchDirectoryViewState.Error -> ErrorScreen()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SearchDirectoryContract.SearchDirectoryEvent.OpenSearchScreen -> onSearchLabelClick()
            }
        }
    }
}

@Composable
fun SuccessScreen(onSearchLabelClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(enabled = true, state = rememberScrollState())
            .background(White200)
    ) {
        NavigationBar()

        SearchView(onSearchLabelClick)

        SearchBackground()
    }
}

@Composable
fun NavigationBar() = TopAppBar(
    title = { Text(text = stringResource(id = R.string.search_title)) },
    elevation = 0.dp
)

@Composable
fun SearchView(onSearchLabelClick: () -> Unit) =
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .defaultMinSize(minHeight = 50.dp)
            .background(color = Black200, shape = RoundedCornerShape(8.dp))
            .clickable { onSearchLabelClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchIcon()

        SearchHint()
    }

@Composable
fun SearchIcon() =
    Image(
        painter = painterResource(id = R.drawable.search_icon),
        contentDescription = "",
        modifier = Modifier.padding(start = 4.dp)
    )

@Composable
fun SearchHint() = Text(
    text = stringResource(id = R.string.search_hint),
    modifier = Modifier.padding(start = 2.dp),
    style = MaterialTheme.typography.subtitle2
)

@Composable
fun SearchBackground() = Column(
    modifier = Modifier
        .padding(top = 140.dp)
        .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Image(
        painter = painterResource(id = R.drawable.worldmap),
        contentDescription = "",
        modifier = Modifier.size(width = 250.dp, height = 140.dp)
    )

    Text(
        text = stringResource(id = R.string.search_description),
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
        style = MaterialTheme.typography.subtitle1
    )
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Preview_Success_Screen() {
    SearchTheme {
        SuccessScreen {

        }
    }
}
