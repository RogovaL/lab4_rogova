package com.example.lab4_rogova

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveLayoutScreen(
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Завдання 1: Адаптивний макет") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AdaptiveLayout(Modifier.padding(paddingValues))
    }
}

@Composable
fun AdaptiveLayout(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0E1))
    ) {
        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    FragmentGreen(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )

                    FragmentBrown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }

                FragmentRed(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                FragmentGreen(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    FragmentBrown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )

                    FragmentRed(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}

@Composable
fun FragmentGreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFF1BA9A6)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Фрагмент 1",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FragmentBrown(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFF7A7259)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Фрагмент 2",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FragmentRed(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFFE96D5B)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Фрагмент 3",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
