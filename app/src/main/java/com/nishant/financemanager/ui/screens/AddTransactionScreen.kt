package com.nishant.financemanager.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.financemanager.R
import com.nishant.financemanager.viewmodel.FinanceViewModel
import com.nishant.financemanager.data.Transaction
import com.nishant.financemanager.data.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModel: FinanceViewModel,
    onBack: () -> Unit
) {

    var isIncome by remember { mutableStateOf(true) }
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Salary") }
    var selectedDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    val categories: List<Pair<String, Int>> = if (isIncome) {
        listOf(
            "Salary" to R.drawable.salary,
            "Freelance" to R.drawable.freelance,
            "Investment" to R.drawable.investment
        )
    } else {
        listOf(
            "Food" to R.drawable.food,
            "Transport" to R.drawable.transport,
            "Shopping" to R.drawable.shopping,
            "Entertainment" to R.drawable.entertrainment,
            "Bills" to R.drawable.bill,
            "Health" to R.drawable.health
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FB))
    ) {

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF0C6ACE),
                            Color(0xFF063770)
                        )
                    )
                )
                .statusBarsPadding()
                .padding(16.dp)
        ) {

            Column {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable { onBack() }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        "Add Transaction",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

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
                        text = "Income",
                        selected = isIncome,
                        onClick = { isIncome = true }
                    )

                    ToggleButton(
                        modifier = Modifier.weight(1f),
                        text = "Expense",
                        selected = !isIncome,
                        onClick = { isIncome = false }
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {

                Text(
                    "Amount",
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("₹ 0.00") },
                    shape = RoundedCornerShape(18.dp),
                    singleLine = true
                )
            }

            item {

                Text(
                    "Category",
                    fontWeight = FontWeight.SemiBold
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    categories.forEach { (category, icon) ->
                        CategoryItem(
                            text = category,
                            icon = icon,
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category }
                        )
                    }
                }
            }

            item {

                Text(
                    "Date",
                    fontWeight = FontWeight.SemiBold
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                ) {

                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        leadingIcon = {
                            Icon(Icons.Default.CalendarToday, null)
                        },
                        placeholder = { Text("Select date") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp)
                    )
                }
            }

            item {

                Text(
                    "Note",
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(18.dp),
                    placeholder = { Text("Add a note...") }
                )
            }

            item {

                Button(
                    onClick = {

                        if (amount.isNotEmpty() && selectedDate.isNotEmpty()) {

                            val transaction = Transaction(
                                title = selectedCategory,
                                amount = amount.toDouble(),
                                type = if (isIncome)
                                    TransactionType.INCOME
                                else
                                    TransactionType.EXPENSE,
                                category = selectedCategory,
                                date = selectedDate,
                                note = note
                            )

                            viewModel.addTransaction(transaction)
                            onBack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0C6ACE)
                    )
                ) {
                    Text(
                        "Save Transaction",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }

    if (showDatePicker) {

        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {

                        datePickerState.selectedDateMillis?.let { millis ->

                            val sdf = java.text.SimpleDateFormat(
                                "dd-MM-yyyy",
                                java.util.Locale.getDefault()
                            )

                            selectedDate = sdf.format(
                                java.util.Date(millis)
                            )
                        }

                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun ToggleButton(
    modifier: Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .background(
                if (selected) Color.White else Color.Transparent,
                RoundedCornerShape(50)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) Color.Black else Color.White
        )
    }
}

@Composable
fun CategoryItem(
    text: String,
    icon: Int,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .shadow(
                elevation = if (selected) 8.dp else 2.dp,
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                if (selected)
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0C6ACE),
                            Color(0xFF063770)
                        )
                    )
                else
                    Brush.verticalGradient(
                        listOf(Color.White, Color.White)
                    ),
                RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp)
            .width(70.dp)
    ) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    if (selected)
                        Color.White.copy(.15f)
                    else
                        Color(0xFFF1F3F6),
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) Color.White else Color.Black
        )
    }
}