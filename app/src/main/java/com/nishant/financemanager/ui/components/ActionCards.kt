package com.nishant.financemanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionCards(
    modifier: Modifier = Modifier,
    onIncomeClick: () -> Unit,
    onExpenseClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        ActionItem(
            modifier = Modifier.weight(1f),
            title = "Add Income",
            iconBg = Color(0xFFE8F5E9),
            iconColor = Color(0xFF00C853),
            isIncome = true,
            onClick = onIncomeClick
        )

        ActionItem(
            modifier = Modifier.weight(1f),
            title = "Add Expense",
            iconBg = Color(0xFFFFEBEE),
            iconColor = Color(0xFFD50000),
            isIncome = false,
            onClick = onExpenseClick
        )
    }
}

@Composable
fun ActionItem(
    modifier: Modifier,
    title: String,
    iconBg: Color,
    iconColor: Color,
    isIncome: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)) // needed for ripple
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(iconBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                if (isIncome)
                    Icons.Default.ArrowDownward
                else
                    Icons.Default.ArrowUpward,
                contentDescription = null,
                tint = iconColor
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            title,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            maxLines = 1,
            softWrap = false
        )
    }
}