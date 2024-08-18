package com.kaz4.foodrecipescompose.ui.components.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaz4.foodrecipescompose.R

@Composable
fun NavigationBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.white))
            .padding(horizontal = 20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.qr_code),
            contentDescription = "qr code",
            modifier = Modifier
                .requiredSize(size = 24.dp)
                .align(alignment = Alignment.CenterEnd)
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .requiredWidth(width = 120.dp)
                .requiredHeight(height = 48.dp)
                .clickable { /*TODO*/ }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Москва",
                    color = Color(0xff222831),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Icon(
                    painter = painterResource(id= R.drawable.keyboard_arrow_down),
                    contentDescription = "arrow down"
                )
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 56, )
@Composable
private fun NavigationBarPreview() {
    NavigationBar(Modifier)
}