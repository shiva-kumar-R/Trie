package com.example.search.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.search.ui.screens.SearchDirectory
import com.example.search.ui.screens.SearchScreen
import com.example.search.ui.theme.SearchTheme
import com.example.search.viewmodel.SearchDirectoryViewModel
import com.example.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchTheme {
                SearchApp()
            }
        }
    }
}

@Composable
fun SearchApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "destination_search_directory") {
        composable(route = "destination_search_directory") {
            val viewModel: SearchDirectoryViewModel = hiltViewModel()
            SearchDirectory(viewModel) {
                navController.navigate(route = "destination_search")
            }
        }

        composable(route = "destination_search") {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreen(viewModel) {

            }
        }
    }
}