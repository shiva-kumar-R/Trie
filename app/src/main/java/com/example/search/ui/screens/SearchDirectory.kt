package com.example.search.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.search.ui.contract.SearchDirectoryContract
import com.example.search.ui.contract.SearchDirectoryContract.SearchDirectoryViewState
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

}
