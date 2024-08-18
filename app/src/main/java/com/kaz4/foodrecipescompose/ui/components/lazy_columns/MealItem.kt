package com.kaz4.foodrecipescompose.ui.components.lazy_columns

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaz4.domain.models.MealItem
import com.kaz4.foodrecipescompose.R
import com.kaz4.foodrecipescompose.ui.fonts.robotoFontFamily

@Composable
fun Meals(
    mealItems: List<MealItem>
) {
    LazyColumn(
        modifier = Modifier
            .background(colorResource(id = R.color.white))
    ) {
        items(items = mealItems) { meal ->
            MealItemView(mealItem = meal)
        }
    }
}

@Composable
fun MealItemView(
    mealItem: MealItem
) {
    HorizontalDivider(
        modifier = Modifier
            .padding(
                start = 10.dp,
                end = 10.dp,
                bottom = 6.dp
            )
            .alpha(0.07f),
        color = colorResource(id = R.color.black)
    )
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
    ) {
        Image(
            //painter = rememberAsyncImagePainter(model = mealItem.image),
            painter = painterResource(id = mealItem.testImage),
            contentDescription = mealItem.title
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = mealItem.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = robotoFontFamily
                ),
                color = colorResource(id = R.color.black)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                text = mealItem.description,
                style = TextStyle(
                    fontSize = 14.sp,
                ),
                color = colorResource(id = R.color.hint)
            )
            AddToCartBtn(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun AddToCartBtn(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .requiredWidth(IntrinsicSize.Min)
            .clip(shape = RoundedCornerShape(6.dp))
            .border(
                border = BorderStroke(
                    1.dp, colorResource(id = R.color.elements_pink)
                ),
            )
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_ingredients_to_shop_list),
            color = colorResource(id = R.color.elements_pink),
            style = TextStyle(
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview
fun PreviewMealsLazyColumn() {
    val mealItems: List<MealItem> = listOf(
        MealItem(
            id = "1",
            testImage = R.drawable.pizza,
            title = "Паста с морепро дукт а миморе продук тамимор епродуктамиепродуктамиепродуктами",
            category = "Итальянская кухня",
            description = "Баварские колбаски, ветчина,пикантная пепперони, острая чоризо,томатный соус."
        ),
        MealItem(
            id = "2",
            testImage = R.drawable.banner_first,
            title = "Салат Цезарь",
            category = "Салаты",
            description = "Баварские колбаски, ветчина,пикантная пепперони, острая чоризо,томатный соус."
        ),
        MealItem(
            id = "2",
            testImage = R.drawable.pizza,
            title = "Салат Цезарь",
            category = "Салаты",
            description = "Баварские колбаски, ветчина,пикантная пепперони, острая чоризо,томатный соус."
        ),
        MealItem(
            id = "2",
            testImage = R.drawable.pizza,
            title = "Салат Цезарь",
            category = "Салаты",
            description = "Классический салат с куриной грудкой, сухариками и соусом Цезарь."
        ),
        MealItem(
            id = "2",
            testImage = R.drawable.pizza,
            title = "Салат Цезарь",
            category = "Салаты",
            description = "Классический салат с куриной грудкой, сухариками и соусом Цезарь."
        ),
    )
    Meals(mealItems = mealItems)
}