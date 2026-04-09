package com.nishant.financemanager.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nishant.financemanager.data.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDelete(
    transaction: Transaction,
    onDelete: () -> Unit
) {

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->

            if (
                value == SwipeToDismissBoxValue.EndToStart ||
                value == SwipeToDismissBoxValue.StartToEnd
            ) {
                onDelete()
            }

            false // VERY IMPORTANT → prevents auto delete
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,

        backgroundContent = {

            val progress = dismissState.progress

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .scale(0.8f + (progress * 0.3f)),
                    contentAlignment = Alignment.Center
                ) {

                    Surface(
                        shape = CircleShape,
                        color = Color(0x22FF0000),
                        tonalElevation = 6.dp
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        },

        content = {
            TransactionItem(transaction)
        }
    )
}