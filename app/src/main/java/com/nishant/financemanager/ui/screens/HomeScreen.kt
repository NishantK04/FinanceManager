package com.nishant.financemanager.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nishant.financemanager.data.Transaction
import com.nishant.financemanager.ui.components.*
import com.nishant.financemanager.viewmodel.FinanceViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    viewModel: FinanceViewModel,
    darkMode: Boolean,
    onToggleTheme: () -> Unit
) {

    var showAdd by remember { mutableStateOf(false) }

    val transactions by viewModel.transactions.collectAsState(initial = emptyList())
    val balance by viewModel.balance.collectAsState(initial = 0.0)
    val income by viewModel.totalIncome.collectAsState(initial = 0.0)
    val expense by viewModel.totalExpense.collectAsState(initial = 0.0)

    // loading state
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(transactions) {
        loading = false
    }

    // delete dialog
    var deleteItem by remember { mutableStateOf<Transaction?>(null) }

    val fabScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // LOADING BAR
        AnimatedVisibility(
            visible = loading,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp),
                color = Color(0xFF063770)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {

            item {
                GradientHeader(
                    name = "There",
                    darkMode = darkMode,
                    onToggleTheme = onToggleTheme
                )
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
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }

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

                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically { it / 2 } + fadeIn(),
                        exit = fadeOut()
                    ) {

                        Box(
                            modifier = Modifier.animateItem()
                        ) {
                            SwipeToDelete(
                                transaction = transaction,
                                onDelete = {
                                    deleteItem = transaction
                                }
                            )
                        }
                    }
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
                .scale(fabScale)
        ) {
            Icon(Icons.Default.Add, null, tint = Color.White)
        }

        if (showAdd) {
            AddTransactionScreen(
                viewModel = viewModel,
                onBack = { showAdd = false }
            )
        }

        // DELETE CONFIRMATION DIALOG
        deleteItem?.let { transaction ->

            AlertDialog(
                onDismissRequest = { deleteItem = null },

                title = {
                    Text("Delete Transaction?")
                },

                text = {
                    Text("Are you sure you want to delete this transaction?")
                },

                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteTransaction(transaction)
                            deleteItem = null
                        }
                    ) {
                        Text(
                            "Yes",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },

                dismissButton = {
                    TextButton(
                        onClick = { deleteItem = null }
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}