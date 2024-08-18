package com.kaz4.foodrecipescompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kaz4.foodrecipescompose.ui.components.lazy_columns.Banners
import com.kaz4.foodrecipescompose.ui.components.lazy_columns.CategoryRow
import com.kaz4.foodrecipescompose.ui.components.lazy_columns.Meals

@Composable
fun ShowCatalogScreen(){
    Column {
        Banners()
//        CategoryRow()
//        Meals()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCatalogScreen(){
    ShowCatalogScreen()
}