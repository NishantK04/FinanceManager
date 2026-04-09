package com.nishant.financemanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.financemanager.ui.theme.*

@Composable
fun GradientHeader(
    name: String,
    darkMode: Boolean,
    onToggleTheme: () -> Unit
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(PurpleStart, PurpleEnd)
                ),
                shape = RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            )
            .padding(top = 30.dp, start = 20.dp, end = 20.dp)
    ) {

        Column(
            modifier = Modifier.padding(top = 10.dp)
        ){

            Text("Hello,", color = Color.White)

            Text(
                text = name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // toggle button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 10.dp, end = 4.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(.2f))
                .clickable { onToggleTheme() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector =
                    if (darkMode)
                        Icons.Default.LightMode   // sun
                    else
                        Icons.Default.DarkMode,  // moon
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}