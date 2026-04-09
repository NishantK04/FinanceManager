package com.nishant.financemanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.financemanager.R
import com.nishant.financemanager.data.TransactionType
import com.nishant.financemanager.ui.components.CategoryCard
import com.nishant.financemanager.viewmodel.FinanceViewModel

@Composable
fun CategoryScreen(viewModel: FinanceViewModel) {

    val transactions by viewModel.transactions.collectAsState()

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var isIncome by remember { mutableStateOf(false) }

    val incomeCategories = listOf(
        "Salary" to R.drawable.salary,
        "Freelance" to R.drawable.freelance,
        "Investment" to R.drawable.investment
    )

    val expenseCategories = listOf(
        "Food" to R.drawable.food,
        "Transport" to R.drawable.transport,
        "Shopping" to R.drawable.shopping,
        "Entertainment" to R.drawable.entertrainment,
        "Bills" to R.drawable.bill,
        "Health" to R.drawable.health
    )

    val categories = if (isIncome) incomeCategories else expenseCategories

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
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
                    "Categories",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // TOGGLE
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White.copy(.15f),
                            RoundedCornerShape(50)
                        )
                        .padding(4.dp)
                ) {

                    ToggleButton(
                        modifier = Modifier.weight(1f),
                        text = "Expense",
                        selected = !isIncome,
                        onClick = {
                            isIncome = false
                            selectedCategory = null
                        }
                    )

                    ToggleButton(
                        modifier = Modifier.weight(1f),
                        text = "Income",
                        selected = isIncome,
                        onClick = {
                            isIncome = true
                            selectedCategory = null
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // GRID
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(categories) { (category, icon) ->

                CategoryCard(
                    category = category,
                    icon = icon,
                    onClick = {
                        selectedCategory = category
                    }
                )
            }
        }

        // TOTAL CARD
        selectedCategory?.let { category ->

            val total = transactions
                .filter {
                    it.category == category &&
                            it.type == if (isIncome)
                        TransactionType.INCOME
                    else
                        TransactionType.EXPENSE
                }
                .sumOf { it.amount }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(18.dp)
                ) {

                    Text(
                        "Total $category ${if (isIncome) "Income" else "Expense"}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        "₹$total",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isIncome)
                            Color(0xFF00C853)
                        else
                            Color(0xFF0C6ACE)
                    )
                }
            }
        }
    }
}