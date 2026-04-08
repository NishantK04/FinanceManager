package com.nishant.financemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.nishant.financemanager.data.FinanceDatabase
import com.nishant.financemanager.navigation.BottomNav
import com.nishant.financemanager.ui.theme.FinanceManagerTheme
import com.nishant.financemanager.viewmodel.FinanceViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            FinanceDatabase::class.java,
            "finance_db"
        ).build()

        setContent {

            val viewModel = FinanceViewModel(db.dao())

            FinanceManagerTheme {
                BottomNav(viewModel)
            }
        }
    }
}