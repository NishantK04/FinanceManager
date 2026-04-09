package com.nishant.financemanager.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.financemanager.R

@Composable
fun BalanceCard(
    balance: Double,
    income: Double,
    expense: Double
) {

    val animatedBalance by animateFloatAsState(balance.toFloat(), label = "")
    val animatedIncome by animateFloatAsState(income.toFloat(), label = "")
    val animatedExpense by animateFloatAsState(expense.toFloat(), label = "")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .offset(y = (-50).dp)
            .height(210.dp)
            .shadow(18.dp, RoundedCornerShape(24.dp))
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0xFF5FA8FF),
                        Color(0xFF7C9EFF)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {

        Image(
            painter = painterResource(R.drawable.rupee),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(80.dp)
                .alpha(0.88f)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    "Total Balance",
                    color = Color.White.copy(.8f),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    "₹ ${"%.0f".format(animatedBalance)}",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                BalanceChip(
                    title = "Income",
                    amount = animatedIncome,
                    color = Color(0xFF00E676)
                )

                BalanceChip(
                    title = "Expense",
                    amount = animatedExpense,
                    color = Color(0xFFFF5252)
                )
            }
        }
    }
}

@Composable
private fun BalanceChip(
    title: String,
    amount: Float,
    color: Color
) {

    Column {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                title,
                color = Color.White.copy(.9f),
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            "₹ ${"%.0f".format(amount)}",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}