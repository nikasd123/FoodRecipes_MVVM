package com.kaz4.foodrecipescompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaz4.foodrecipescompose.ui.components.lazy_columns.Banners
import com.kaz4.foodrecipescompose.ui.components.lazy_columns.CategoryRow
import com.kaz4.foodrecipescompose.ui.components.lazy_columns.Meals
import com.kaz4.foodrecipescompose.ui.view_models.CatalogViewModel

@Composable
fun CatalogScreen(viewModel: CatalogViewModel = hiltViewModel()){
    val categories by viewModel.categories.collectAsState()
    val meals by viewModel.meals.collectAsState()

    val selectedCategory = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Banners()
        categories?.let { CategoryRow(it, selectedCategory) }
        meals?.let { Meals(it) }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCatalogScreen(){
    CatalogScreen()
}