package com.example.search.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.search.ui.contract.SearchContract
import com.example.search.ui.theme.White200
import com.example.search.viewmodel.SearchViewModel

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

        CountriesResults()
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
fun CountriesResults() {

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
