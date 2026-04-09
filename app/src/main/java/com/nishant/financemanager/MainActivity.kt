package com.nishant.financemanager

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.room.Room
import com.nishant.financemanager.data.FinanceDatabase
import com.nishant.financemanager.navigation.BottomNav
import com.nishant.financemanager.ui.screens.SplashScreen
import com.nishant.financemanager.ui.theme.FinanceManagerTheme
import com.nishant.financemanager.ui.theme.PurpleStart
import com.nishant.financemanager.ui.theme.ThemePreference
import com.nishant.financemanager.viewmodel.FinanceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            FinanceDatabase::class.java,
            "finance_db"
        ).build()

        setContent {

            val view = LocalView.current

            // SET STATUS BAR COLOR GLOBAL
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = PurpleStart.toArgb()
            }

            val viewModel = FinanceViewModel(db.dao())
            val context = LocalContext.current
            val prefs = remember { ThemePreference(context) }

            val darkMode by prefs.isDarkMode.collectAsState(initial = false)

            var showSplash by remember { mutableStateOf(true) }

            if (showSplash) {

                SplashScreen(
                    onFinish = { showSplash = false }
                )

            } else {

                FinanceManagerTheme(darkTheme = darkMode) {

                    BottomNav(
                        viewModel = viewModel,
                        darkMode = darkMode,
                        onToggleTheme = {
                            CoroutineScope(Dispatchers.IO).launch {
                                prefs.saveDarkMode(!darkMode)
                            }
                        }
                    )
                }
            }
        }
    }
}