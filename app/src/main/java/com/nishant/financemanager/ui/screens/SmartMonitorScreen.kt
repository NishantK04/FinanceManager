package com.nishant.financemanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.financemanager.R
import com.nishant.financemanager.viewmodel.FinanceViewModel

@Composable
fun SmartMonitorScreen(
    viewModel: FinanceViewModel,
    onBack: () -> Unit
) {

    val isEnabled by viewModel.monitoringEnabled.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {

        item {
            val context = LocalContext.current

            MonitorHeader(
                isEnabled = isEnabled,
                onToggle = {
                    viewModel.toggleMonitoring(context, !isEnabled)
                },
                onBack = onBack
            )
        }

        item {
            StatsSection()
        }

        item {
            WeeklyOverviewCard()
        }

        item {
            RecentActivitySection()
        }
    }
}

@Composable
fun MonitorHeader(
    isEnabled: Boolean,
    onToggle: () -> Unit,
    onBack: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp) // 🔥 increased height
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF7B2FF7), Color(0xFF2E6BFF))
                ),
                RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
            )
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween // 🔥 KEY FIX
        ) {

            // 🔹 Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable { onBack() }
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text("Smart Monitoring", color = Color.White)

                    Spacer(modifier = Modifier.width(8.dp))

                    Switch(
                        checked = isEnabled,
                        onCheckedChange = { onToggle() }
                    )
                }
            }

            // 🔹 Center Content
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.White.copy(.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.sheild),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp)) // 🔥 reduced

                Text(
                    "Smart Monitoring",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Financial Protection System",
                    color = Color.White.copy(.8f),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp)) // 🔥 breathing space
        }
    }
}

@Composable
fun StatsSection() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-28).dp) // 🔥 overlap effect
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        StatCard("Money Saved", "₹2,450", Color(0xFF00C853), Modifier.weight(1f))
        StatCard("Warnings", "12", Color(0xFFFF6D00), Modifier.weight(1f))
        StatCard("Prevented", "5", Color(0xFFD50000), Modifier.weight(1f))
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .shadow(12.dp, RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(vertical = 14.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color.copy(0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(title, fontSize = 11.sp, color = Color.Gray)

        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun WeeklyOverviewCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-16).dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text("Weekly Overview", fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Chart coming soon 🔥")
        }
    }
}

@Composable
fun RecentActivitySection() {

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        Text(
            "Recent Activity",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        ActivityItem("Stopped overspending on Shopping", "₹450", "1h ago")
        ActivityItem("Food budget warning triggered", "₹320", "2h ago")
        ActivityItem("Smart savings detected", "₹200", "1d ago")
        ActivityItem("Transport spending alert", "₹180", "2d ago")
        ActivityItem("Prevented impulse purchase", "₹600", "3d ago")
    }
}

@Composable
fun ActivityItem(title: String, amount: String, time: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .shadow(4.dp, RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(14.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(title, fontSize = 13.sp)
            Text(amount, color = Color(0xFF7B2FF7), fontSize = 12.sp)
        }

        Text(time, fontSize = 11.sp, color = Color.Gray)
    }
}