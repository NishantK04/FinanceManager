package com.nishant.financemanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.financemanager.R
import com.nishant.financemanager.data.Transaction
import com.nishant.financemanager.data.TransactionType

@Composable
fun TransactionItem(transaction: Transaction) {

    val isIncome = transaction.type == TransactionType.INCOME

    val amountColor =
        if (isIncome) Color(0xFF0E853E)
        else Color(0xFFDA0F0F)

    val iconBg =
        if (isIncome) Color(0xFFE8F5E9)
        else Color(0xFFFFEBEE)

    val icon = when (transaction.category) {
        "Salary" -> R.drawable.salary
        "Freelance" -> R.drawable.freelance
        "Investment" -> R.drawable.investment
        "Food" -> R.drawable.food
        "Transport" -> R.drawable.transport
        "Shopping" -> R.drawable.shopping
        "Entertrain" -> R.drawable.entertrainment
        "Bills" -> R.drawable.bill
        "Health" -> R.drawable.health
        else -> R.drawable.food
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .shadow(6.dp, RoundedCornerShape(18.dp))
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(18.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(44.dp)
                .background(iconBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = transaction.category,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = transaction.date,
                fontSize = 12.sp,
                color = Color.Gray
            )

            // NOTE TEXT
            if (transaction.note.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = transaction.note,
                    fontSize = 12.sp,
                    color = Color(0xFF666666)
                )
            }
        }

        Text(
            text = "${if (isIncome) "+" else "-"}₹${transaction.amount}",
            color = amountColor,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}