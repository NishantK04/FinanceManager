package com.nishant.financemanager.navigation

import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlin.math.sin

import com.nishant.financemanager.ui.screens.CategoryScreen
import com.nishant.financemanager.ui.screens.HomeScreen
import com.nishant.financemanager.ui.screens.SummaryScreen
import com.nishant.financemanager.viewmodel.FinanceViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNav(
    viewModel: FinanceViewModel,
    darkMode: Boolean,
    onToggleTheme: () -> Unit
) {

    var selected by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {

            AnchoredGreenBottomBar(

                items = listOf(
                    Icons.Default.Home,
                    Icons.Default.List,
                    Icons.Default.PieChart
                ),

                selectedIndex = selected,

                barColor = Color(0xFF063770),
                activeColor = Color(0xFF063770),

                inactiveColor = Color.White,

                onItemSelected = {
                    selected = it
                }
            )
        }
    ) { _ ->

        AnimatedContent(
            targetState = selected,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                ) + fadeIn() with
                        slideOutHorizontally(
                            animationSpec = tween(300),
                            targetOffsetX = { -it }
                        ) + fadeOut()
            }
        ) { screen ->

            when (screen) {
                0 -> HomeScreen(viewModel, darkMode , onToggleTheme)
                1 -> CategoryScreen(viewModel)
                2 -> SummaryScreen(viewModel)
            }
        }
    }
}
@Composable
fun AnchoredGreenBottomBar(
    items: List<ImageVector>,
    selectedIndex: Int,
    barColor: Color,
    activeColor: Color,
    inactiveColor: Color,
    onItemSelected: (Int) -> Unit
) {
    val barHeight = 72.dp
    val topPadding = 52.dp
    val totalHeight = barHeight + topPadding

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val usableWidth = screenWidth - 32.dp
    val tabWidth = usableWidth / items.size

    val density = LocalDensity.current
    val tabWidthPx = with(density) { tabWidth.toPx() }
    val topYPx = remember(topPadding, density) { with(density) { topPadding.toPx() } }
    val barHeightPx = remember(barHeight, density) { with(density) { barHeight.toPx() } }

    val syncStiffness = 250f
    val syncDamping = 0.75f
    val syncSpring = remember { spring<Float>(dampingRatio = syncDamping, stiffness = syncStiffness) }

    val animatedCutoutX by animateFloatAsState(
        targetValue = (selectedIndex * tabWidthPx) + (tabWidthPx / 2f),
        animationSpec = syncSpring,
        label = "CutoutSlide"
    )

    val onItemSelectedState by rememberUpdatedState(onItemSelected)

    // Reusable Path for the bar shape
    val barPath = remember { Path() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp)
            .height(totalHeight)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cornerRadius = 16.dp.toPx()
            val circleRadius = 32.dp.toPx()
            val gap = 8.dp.toPx()
            val cutoutRadius = circleRadius + gap
            val dipDepth = cutoutRadius
            val shoulderWidth = 16.dp.toPx()

            val topY = topYPx
            val bottomYBar = topY + barHeightPx
            val cx = animatedCutoutX

            val safeLeft = cornerRadius
            val safeRight = size.width - cornerRadius
            val leftShoulderX = (cx - cutoutRadius - shoulderWidth).coerceAtLeast(safeLeft)
            val leftCp1X = (cx - cutoutRadius).coerceAtLeast(leftShoulderX)
            val leftCp2X = (cx - cutoutRadius).coerceAtLeast(leftShoulderX)
            val rightCp1X = (cx + cutoutRadius).coerceAtMost(safeRight)
            val rightCp2X = (cx + cutoutRadius).coerceAtMost(safeRight)
            val rightShoulderX =
                (cx + cutoutRadius + shoulderWidth).coerceAtMost(safeRight).coerceAtLeast(rightCp2X)

            barPath.reset()
            barPath.moveTo(0f, topY + cornerRadius)
            barPath.arcTo(
                Rect(0f, topY, 2 * cornerRadius, topY + 2 * cornerRadius), 180f, 90f, false
            )
            barPath.lineTo(leftShoulderX, topY)
            barPath.cubicTo(leftCp1X, topY, leftCp2X, topY + dipDepth, cx, topY + dipDepth)
            barPath.cubicTo(rightCp1X, topY + dipDepth, rightCp2X, topY, rightShoulderX, topY)
            barPath.lineTo(size.width - cornerRadius, topY)
            barPath.arcTo(
                Rect(size.width - 2 * cornerRadius, topY, size.width, topY + 2 * cornerRadius),
                -90f, 90f, false
            )
            barPath.lineTo(size.width, bottomYBar - cornerRadius)
            barPath.arcTo(
                Rect(
                    size.width - 2 * cornerRadius,
                    bottomYBar - 2 * cornerRadius,
                    size.width,
                    bottomYBar
                ),
                0f, 90f, false
            )
            barPath.lineTo(cornerRadius, bottomYBar)
            barPath.arcTo(
                Rect(0f, bottomYBar - 2 * cornerRadius, 2 * cornerRadius, bottomYBar),
                90f, 90f, false
            )
            barPath.close()

            drawPath(path = barPath, color = barColor)

            val circleCenterY = topY


// main bubble
            drawCircle(
                color = Color(0xFFF8FAFF),
                radius = circleRadius,
                center = Offset(cx, circleCenterY)
            )

// strong border
            drawCircle(
                color = Color(0xFF063770),
                radius = circleRadius,
                center = Offset(cx, circleCenterY),
                style = Stroke(width = 5.dp.toPx())
            )
        }

        Row(modifier = Modifier.fillMaxSize()) {
            items.forEachIndexed { index, icon ->
                val isSelected = selectedIndex == index
                val animatedIconSize by animateDpAsState(
                    targetValue = if (isSelected) 36.dp else 26.dp,
                    animationSpec = spring(dampingRatio = syncDamping, stiffness = syncStiffness),
                    label = "IconSize"
                )
                val animatedIconSizePx = with(density) { animatedIconSize.toPx() }
                val animatedIconColor by animateColorAsState(
                    targetValue = if (isSelected) activeColor else inactiveColor,
                    animationSpec = spring(dampingRatio = syncDamping, stiffness = syncStiffness),
                    label = "IconColor"
                )

                val targetCenterYPx = if (isSelected) topYPx else topYPx + (barHeightPx / 2f)
                val currentCenterYPx by animateFloatAsState(
                    targetValue = targetCenterYPx,
                    animationSpec = spring(dampingRatio = syncDamping, stiffness = syncStiffness),
                    label = "IconY"
                )

                val effectProgress by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0f,
                    animationSpec = spring(dampingRatio = syncDamping, stiffness = syncStiffness),
                    label = "EffectProgress"
                )

                val iconRotation = when (index) {
                    0 -> (sin(effectProgress * Math.PI * 3) * 15f).toFloat(); 1 -> effectProgress * 360f; else -> 0f
                }
                val iconScaleX = when (index) {
                    2 -> 1f - (sin(effectProgress * Math.PI) * 0.15f).toFloat(); else -> 1f
                }
                val iconScaleY = when (index) {
                    2 -> 1f + (sin(effectProgress * Math.PI) * 0.4f).toFloat(); else -> 1f
                }
                val iconOrigin = when (index) {
                    2 -> androidx.compose.ui.graphics.TransformOrigin(
                        0.5f,
                        1f
                    ); else -> androidx.compose.ui.graphics.TransformOrigin.Center
                }

                val stableOnClick = remember(index, onItemSelectedState) {
                    { onItemSelectedState(index) }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = stableOnClick
                        ),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Icon(
                        imageVector = icon, contentDescription = null, tint = animatedIconColor,
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    0,
                                    (currentCenterYPx - animatedIconSizePx / 2f).roundToInt()
                                )
                            }
                            .size(animatedIconSize)
                            .graphicsLayer {
                                rotationZ = iconRotation; scaleX = iconScaleX; scaleY =
                                iconScaleY; transformOrigin = iconOrigin
                            }
                    )
                }
            }
        }
    }
}

