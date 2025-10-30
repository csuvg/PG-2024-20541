package com.app.quauhtlemallan.ui.view.navbar.games

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.quauhtlemallan.R
import com.app.quauhtlemallan.data.model.Category
import com.app.quauhtlemallan.ui.view.navbar.BottomNavigationBar
import com.app.quauhtlemallan.ui.view.navbar.achievements.CategoryCard

@Composable
fun CategoriesGamesScreen(
    navController: NavHostController,
    navigateBack: () -> Unit
) {
    val categories = listOf(
        Category(id = "artes", title = "Artes", imageUrl = R.drawable.ic_arte),
        Category(id = "cultura", title = "Cultura", imageUrl = R.drawable.ic_cultura),
        Category(id = "deportes", title = "Deportes", imageUrl = R.drawable.ic_deporte),
        Category(id = "gastronomia", title = "Gastronomía", imageUrl = R.drawable.ic_comida),
        Category(id = "geografia", title = "Geografía", imageUrl = R.drawable.ic_geo),
        Category(id = "historia", title = "Historia", imageUrl = R.drawable.ic_historia),
        Category(id = "idiomas", title = "Idiomas", imageUrl = R.drawable.ic_idiomas),
        Category(id = "turismo", title = "Turismo", imageUrl = R.drawable.ic_turismo)
    )

    val borderColors = listOf(
        Color(0xFFEBB006),
        Color(0xFFCA6702),
        Color(0xFF005F73),
        Color(0xFF94D2BD),
        Color(0xFF7D5260),
        Color(0xFFEE9B00),
        Color(0xFF184E77),
        Color(0xFFB5E48C)
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_24),
                    contentDescription = "Regresar",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navigateBack()
                        }
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(categories.size) { index ->
                    CategoryCard(
                        category = categories[index],
                        borderColor = borderColors[index],
                        onClick = {
                            navController.navigate("games/${categories[index].id}")
                        }
                    )
                }
            }
        }
    }
}