# Finance Manager App

##  Overview
A clean and modern Finance Manager / Expense Tracker Android application built using Kotlin + Jetpack Compose.
This app helps users track income, expenses, and view monthly financial summaries with a fintech-style UI.

---
##  Screenshots  
<img src="https://github.com/NishantK04/FinanceManager/blob/master/splash.png" width="150"> <img src="https://github.com/NishantK04/FinanceManager/blob/master/homelight.png" width="150"> <img src="https://github.com/NishantK04/FinanceManager/blob/master/addTD1.png" width="150"> <img src="https://github.com/NishantK04/FinanceManager/blob/master/addInLight2.png" width="150"> <img src="https://github.com/NishantK04/FinanceManager/blob/master/categoriesLight.png" width="150"> <img src="https://github.com/NishantK04/FinanceManager/blob/master/categoriesLight2.png" width="150"> <img src="https://github.com/NishantK04/FinanceManager/blob/master/summaryLight.png" width="150">

---
##  Project Structure
```sh
com.nishant.financemanager
│
├── ui
│   ├── screens
│   │   ├── HomeScreen.kt
│   │   ├── AddTransactionScreen.kt
│   │   ├── CategoryScreen.kt
│   │   ├── SummaryScreen.kt
│   │   └── SplashScreen.kt
│   │
│   ├── components
│   │   ├── BalanceCard.kt
│   │   ├── TransactionItem.kt
│   │   ├── CategoryCard.kt
│   │   ├── GradientHeader.kt
│   │   ├── ActionCards.kt
│   │   ├── SwipeToDelete.kt
│   │   └── EmptyTransaction.kt
│   │
│   └── theme
│
├── navigation
│   └── BottomNav.kt
│
├── viewmodel
│   └── FinanceViewModel.kt
│
├── data
│   ├── FinanceDatabase.kt
│   ├── TransactionDao.kt
│   ├── Converters.kt
│   └── Transaction.kt
│   
└── MainActivity.kt


```

---

##  Features
- Add Income & Expense
- Category-based transaction tracking
- Monthly financial summary
- Remaining balance calculation
- Gradient fintech-style UI
- Dark / Light mode toggle
- Bottom tab navigation
- Smooth animations
- Form validation
- Local data storage
- Clean MVVM architecture

---

##  Tech Stack
- Kotlin
- Jetpack Compose
- MVVM Architecture
- Material 3
- Local Storage (Room / DataStore / SharedPreferences)
- Android Studio  

---

## App Functionality
1. Add Transactions
  - Add income or expense
  - Enter amount
  - Select category
  - Pick date
  - Add optional note
2. Category Tracking
  - Predefined categories
  - Icons and colors
  - Easy filtering
3. Monthly Summary
  - Total income
  - Total expense
  - Remaining balance
  - Visual cards

---

## Dark / Light Mode
- Toggle theme anytime
- Smooth UI transition
- Fintech gradient support

##  Installation & Setup
```sh
# Clone the repository
git clone https://github.com/yourusername/FinanceManager.git

# Open the project in Android Studio

# Sync Gradle dependencies

# Run on emulator or physical device
