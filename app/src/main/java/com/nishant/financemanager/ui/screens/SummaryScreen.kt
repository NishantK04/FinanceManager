package com.nishant.financemanager.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.financemanager.data.TransactionType
import com.nishant.financemanager.viewmodel.FinanceViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun SummaryScreen(viewModel: FinanceViewModel) {

    var selectedMonth by remember { mutableStateOf(YearMonth.now()) }

    val transactions by viewModel.transactions.collectAsState()

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    val filtered = remember(transactions, selectedMonth) {
        transactions.filter {
            val date = LocalDate.parse(it.date, formatter)
            date.month == selectedMonth.month &&
                    date.year == selectedMonth.year
        }
    }

    val income =
        filtered.filter { it.type == TransactionType.INCOME }
            .sumOf { it.amount }

    val expense =
        filtered.filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amount }

    val balance = income - expense

    val categoryTotals =
        filtered
            .filter { it.type == TransactionType.EXPENSE }
            .groupBy { it.category }
            .mapValues { it.value.sumOf { t -> t.amount } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            SummaryHeader(
                selectedMonth = selectedMonth,
                onPrev = { selectedMonth = selectedMonth.minusMonths(1) },
                onNext = { selectedMonth = selectedMonth.plusMonths(1) }
            )
        }

        item {
            SummaryCards(income, expense, balance)
        }

        item {
            SpendingByCategoryCard(categoryTotals)
        }

        item {
            Last7DaysCard()
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun SummaryHeader(
    selectedMonth: YearMonth,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF063770),
                        Color(0xFF0C6ACE)
                    )
                ),
                RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            )
            .statusBarsPadding()
            .padding(20.dp)
    ) {

        Column {

            Text(
                "Summary",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            MonthSelector(
                selectedMonth = selectedMonth,
                onPrev = onPrev,
                onNext = onNext
            )
        }
    }
}

@Composable
fun MonthSelector(
    selectedMonth: YearMonth,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {

    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White.copy(.15f),
                RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            Icons.Default.KeyboardArrowLeft,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable { onPrev() }
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = selectedMonth
                    .format(formatter)
                    .uppercase(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }

        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable { onNext() }
        )
    }
}

@Composable
fun SummaryCards(
    income: Double,
    expense: Double,
    balance: Double
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        SummaryCard("Income", "₹$income", Color(0xFF00C853), Modifier.weight(1f))
        SummaryCard("Expense", "₹$expense", Color(0xFFFF3D00), Modifier.weight(1f))
        SummaryCard("Balance", "₹$balance", Color(0xFF6C4DFF), Modifier.weight(1f))
    }
}

@Composable
fun SummaryCard(
    title: String,
    amount: String,
    color: Color,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .shadow(6.dp, RoundedCornerShape(14.dp))
            .background(color, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {

        Text(title, color = Color.White, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            amount,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SpendingByCategoryCard(categoryTotals: Map<String, Double>) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "Spending by Category",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(20.dp))

            DonutChart(categoryTotals)

            Spacer(modifier = Modifier.height(20.dp))

            categoryTotals.forEach { (category, amount) ->
                CategoryRow(category, "₹$amount", getCategoryColor(category))
            }
        }
    }
}

@Composable
fun CategoryRow(name: String, amount: String, color: Color) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(name, fontSize = 13.sp)
        }

        Text(amount, fontSize = 13.sp)
    }
}

@Composable
fun Last7DaysCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "Last 7 Days Spending",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Chart coming soon",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun DonutChart(data: Map<String, Double>) {

    val total = data.values.sum()

    val animation = remember { Animatable(0f) }

    LaunchedEffect(data) {
        animation.snapTo(0f)
        animation.animateTo(
            1f,
            animationSpec = tween(
                durationMillis = 900,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.size(170.dp)
        ) {

            var startAngle = -90f
            val stroke = 26.dp.toPx()

            data.forEach { (category, value) ->

                val sweep =
                    if (total == 0.0) 0f
                    else ((value / total) * 360f * animation.value)

                drawArc(
                    color = getCategoryColor(category),
                    startAngle = startAngle,
                    sweepAngle = sweep.toFloat(),
                    useCenter = false,
                    style = Stroke(
                        width = stroke,
                        cap = StrokeCap.Round
                    )
                )

                startAngle += (value / total * 360f).toFloat()
            }
        }

        // center text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Total",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(.6f)
            )

            Text(
                "₹${total.toInt()}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

fun getCategoryColor(category: String): Color {
    return when (category) {
        "Food" -> Color(0xFFEF5350)
        "Shopping" -> Color(0xFFEC407A)
        "Transport" -> Color(0xFFFFA726)
        "Bills" -> Color(0xFF42A5F5)
        "Health" -> Color(0xFF66BB6A)
        "Entertainment" -> Color(0xFFAB47BC)
        else -> Color(0xFF26A69A)
    }
}