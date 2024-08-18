package com.kaz4.foodrecipescompose.ui.components.lazy_columns

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaz4.foodrecipescompose.R

@Composable
fun Banners(modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(items = listOf(R.drawable.banner_first, R.drawable.banner_second)) { banner ->
            Image(
                painter = painterResource(id = banner),
                contentDescription = "Banner",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .requiredWidth(width = 300.dp)
                    .requiredHeight(height = 112.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            )
        }

    }
}

@Preview(widthDp = 616, heightDp = 112)
@Composable
private fun BannersPreview() {
    Banners(Modifier)
}