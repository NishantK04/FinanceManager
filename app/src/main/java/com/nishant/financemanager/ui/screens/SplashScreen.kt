package com.nishant.financemanager.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.financemanager.ui.theme.PurpleEnd
import com.nishant.financemanager.ui.theme.PurpleStart
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinish: () -> Unit
) {

    val scale = remember { Animatable(0.6f) }
    val alpha = remember { Animatable(0f) }

    val infinite = rememberInfiniteTransition(label = "")

    val dot1 by infinite.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(600),
            RepeatMode.Reverse
        ),
        label = ""
    )

    val dot2 by infinite.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(600, delayMillis = 150),
            RepeatMode.Reverse
        ),
        label = ""
    )

    val dot3 by infinite.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(600, delayMillis = 300),
            RepeatMode.Reverse
        ),
        label = ""
    )

    LaunchedEffect(true) {

        scale.animateTo(
            1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy
            )
        )

        alpha.animateTo(
            1f,
            animationSpec = tween(800)
        )

        delay(1800)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        PurpleStart,
                        Color(0xFF2F4FA3),
                        PurpleEnd
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        ) {

            // Logo
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        Color.White.copy(.2f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "₹",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Small tagline only
            Text(
                text = "Track your money smartly",
                color = Color.White.copy(.85f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Dot(dot1)
                Dot(dot2)
                Dot(dot3)
            }
        }
    }
}

@Composable
private fun Dot(alpha: Float) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .alpha(alpha)
            .background(
                Color.White,
                CircleShape
            )
    )
}