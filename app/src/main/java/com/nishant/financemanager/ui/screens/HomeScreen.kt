package com.nishant.financemanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nishant.financemanager.ui.components.*
import com.nishant.financemanager.viewmodel.FinanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: FinanceViewModel) {

    var showAdd by remember { mutableStateOf(false) }

    val transactions by viewModel.transactions.collectAsState()
    val balance by viewModel.balance.collectAsState(initial = 0.0)
    val income by viewModel.totalIncome.collectAsState(initial = 0.0)
    val expense by viewModel.totalExpense.collectAsState(initial = 0.0)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {

            item {
                GradientHeader("Nishant")
            }

            item {
                BalanceCard(balance, income, expense)
            }

            item {
                ActionCards(
                    modifier = Modifier.offset(y = (-38).dp),
                    onIncomeClick = { showAdd = true },
                    onExpenseClick = { showAdd = true }
                )
            }

            stickyHeader {
                Text(
                    "Recent Transactions",
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-22).dp)
                        .background(Color(0xFFF5F5F5))
                        .padding(horizontal = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            // EMPTY STATE
            if (transactions.isEmpty()) {

                item {
                    EmptyTransaction(
                        onAddClick = { showAdd = true }
                    )
                }

            } else {

                items(
                    items = transactions,
                    key = { it.id }
                ) { transaction ->

                    SwipeToDelete(
                        transaction = transaction,
                        onDelete = {
                            viewModel.deleteTransaction(transaction)
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        FloatingActionButton(
            onClick = { showAdd = true },
            containerColor = Color(0xFF063770),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 115.dp)
        ) {
            Icon(Icons.Default.Add, null, tint = Color.White)
        }

        if (showAdd) {
            AddTransactionScreen(
                viewModel = viewModel,
                onBack = { showAdd = false }
            )
        }
    }
}