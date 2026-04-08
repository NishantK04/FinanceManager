package com.nishant.financemanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.financemanager.ui.theme.*

@Composable
fun BalanceCard(
    balance: Double,
    income: Double,
    expense: Double
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .offset(y = (-50).dp)
            .height(200.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(CardGradient1, CardGradient2)
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    "Total Balance",
                    fontSize = 16.sp,
                    color = Color.White.copy(.8f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    "$$balance",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        "Income",
                        color = Color.Green,
                        fontSize = 17.sp
                    )

                    Text(
                        "$$income",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(80.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        "Expense",
                        color = Color.Yellow,
                        fontSize = 16.sp
                    )

                    Text(
                        "$$expense",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}