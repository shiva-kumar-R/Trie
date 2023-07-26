package com.example.search.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.search.ui.contract.SearchContract
import com.example.search.ui.model.CountriesItem
import com.example.search.ui.theme.White200
import com.example.search.viewmodel.SearchViewModel
import java.util.*

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onBackClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White200)
    ) {
        SearchCountries(viewModel, onBackClicked)

        CountriesResults(state = viewModel.viewState.collectAsStateWithLifecycle().value)
    }
}

@Composable
fun SearchCountries(viewModel: SearchViewModel, onBackClicked: () -> Unit) {
    TopAppBar(
        title = {
            SearchField(viewModel)
        },
        modifier = Modifier.defaultMinSize(24.dp, 24.dp),
        navigationIcon = {
            BackButton(onBackClicked)
        },
        elevation = 0.dp
    )
}

@Composable
fun SearchField(viewModel: SearchViewModel) {
    val searchState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = ""))
    }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchState.value,
        onValueChange = { newValue ->
            onValueChange(
                viewModel = viewModel,
                newValue = newValue,
                searchState = searchState
            )
        },
        trailingIcon = {

        },
        placeholder = {
            SearchHint()
        }
    )
}

@Composable
fun BackButton(onBackClicked: () -> Unit) = IconButton(onClick = onBackClicked) {
    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
}

@Composable
fun CountriesResults(state: SearchContract.SearchViewState) {
    when (state) {
        SearchContract.SearchViewState.Loading -> LoadingScreen()
        SearchContract.SearchViewState.Error -> ErrorScreen()
        is SearchContract.SearchViewState.LoadSearchResults -> LoadSearchResultsScreen(details = state.details)
        is SearchContract.SearchViewState.NoSearchResults -> Unit
    }
}

@Composable
fun LoadSearchResultsScreen(details: List<CountriesItem>) =
    LazyColumn(
        modifier = Modifier
            .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxSize()
    ) {
        items(details) { item ->
            when (item) {
                is CountriesItem.HeaderItem -> SearchHeader(item.heading)
                is CountriesItem.CountriesDetailsItem -> SearchItem(item)
            }
        }
    }

@Composable
fun SearchHeader(results: String) =
    Text(
        text = String.format(Locale.ENGLISH, "%s results", results),
        modifier = Modifier.padding(12.dp),
        style = MaterialTheme.typography.subtitle1
    )

@Composable
fun SearchItem(item: CountriesItem.CountriesDetailsItem) = Column {
    Text(
        text = item.countryCode,
        modifier = Modifier
            .padding(start = 18.dp, end = 18.dp, top = 12.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.h6
    )

    Text(
        text = item.country,
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 4.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.body1
    )
}

private fun onValueChange(
    viewModel: SearchViewModel,
    newValue: TextFieldValue,
    searchState: MutableState<TextFieldValue>
) {
    val constraint = newValue.text.trim()
    if (newValue.text != searchState.value.text)
        viewModel.onIntention(
            SearchContract.SearchIntention.SearchCountriesIntention(constraint)
        )
    searchState.value = searchState.value.copy(
        text = constraint,
        selection = newValue.selection,
        composition = newValue.composition
    )
}
