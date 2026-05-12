package com.nishant.financemanager

object SmartMonitorManager {

    fun checkBudget(): String? {

        // 🔥 Replace later with real DB data
        val budget = 3000
        val spent = 2700

        val remaining = budget - spent

        return if (remaining < 500) {
            "⚠️ Only ₹$remaining left in your budget!"
        } else null
    }
}