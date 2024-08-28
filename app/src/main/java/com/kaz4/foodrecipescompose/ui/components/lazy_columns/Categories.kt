package com.kaz4.foodrecipescompose.ui.components.lazy_columns

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaz4.domain.models.Category
import com.kaz4.foodrecipescompose.R

@Composable
fun CategoryRow(
    categories: List<Category>,
    selectedCategory: MutableState<String?>
) {
    LazyRow(
        modifier = Modifier
            .background(color = colorResource(id = R.color.background))
            .fillMaxWidth()
    ) {
        items(items = categories) { category ->
            val isSelected = selectedCategory.value == category.category
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 5.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .clickable {
                        selectedCategory.value = if (isSelected) null else category.category
                    }
            ) {
                Box(
                    modifier = Modifier
                        .shadow(10.dp)
                        .background(
                            color = if (isSelected) {
                                colorResource(id = R.color.background_pink)
                            } else {
                                colorResource(id = R.color.background)
                            }
                        )
                        .wrapContentSize()
                        .padding(vertical = 5.dp, horizontal = 14.dp)
                ) {
                    Text(
                        text = category.category,
                        color = if (isSelected) {
                            colorResource(id = R.color.elements_pink)
                        } else {
                            colorResource(id = R.color.hint)
                        }
                    )
                }
            }
        }
    }
}

private val testList: List<Category> = listOf(
    Category("Test1", "Test1", true),
    Category("Test2", "Test2", false),
    Category("Test3", "Test3", false),
    Category("Test4", "Test4", false),
    Category("Test5", "Test5", false),
)

@Preview
@Composable
fun CategoryRowPreview() {
    val selectedCategory = remember { mutableStateOf<String?>("Test1") }
    CategoryRow(categories = testList, selectedCategory = selectedCategory)
}