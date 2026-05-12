package com.nishant.financemanager

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class MonitorAccessibilityService : AccessibilityService() {

    private var lastTriggerTime = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (event == null) return

        // 🔥 Only useful events
        if (event.eventType != AccessibilityEvent.TYPE_VIEW_CLICKED &&
            event.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        ) return

        val packageName = event.packageName?.toString() ?: return

        // 🔥 Step 1: Filter apps
        if (!isShoppingApp(packageName)) return

        val rootNode = rootInActiveWindow ?: return

        // 🔥 DEBUG (remove later)
        printNodeTree(rootNode)

        Log.d("MONITOR", "App: $packageName")

        // 🔥 Step 2: Node-based detection (IMPORTANT FIX)
        if (findActionNode(rootNode) && shouldTrigger()) {

            Log.d("MONITOR", "⚠️ Shopping action detected!")

            val warning = SmartMonitorManager.checkBudget()

            warning?.let {
                OverlayManager.show(this, it)
            }
        }
    }

    override fun onInterrupt() {}

    // 🔥 Cooldown (prevents spam)
    private fun shouldTrigger(): Boolean {
        val now = System.currentTimeMillis()
        if (now - lastTriggerTime > 3000) {
            lastTriggerTime = now
            return true
        }
        return false
    }

    // 🔥 App filter
    private fun isShoppingApp(pkg: String): Boolean {
        return pkg.contains("amazon", true) ||
                pkg.contains("flipkart", true) ||
                pkg.contains("myntra", true)
    }

    // 🔥 REAL detection logic (based on your logs)
    private fun findActionNode(node: AccessibilityNodeInfo?): Boolean {
        if (node == null) return false

        val text = node.text?.toString() ?: ""
        val desc = node.contentDescription?.toString() ?: ""

        // 🔥 Flipkart-specific behavior
        if (
            desc.contains("go to cart", true) ||   // after add
            desc.contains("buy", true) ||          // broken "buy"
            text.contains("buy", true)
        ) {
            return true
        }

        for (i in 0 until node.childCount) {
            if (findActionNode(node.getChild(i))) return true
        }

        return false
    }

    // 🔥 DEBUG TREE (remove after testing)
    private fun printNodeTree(node: AccessibilityNodeInfo?, depth: Int = 0) {
        if (node == null) return

        val indent = " ".repeat(depth * 2)

        val text = node.text?.toString()
        val desc = node.contentDescription?.toString()
        val className = node.className?.toString()

        Log.d("TREE", "$indent class=$className text=$text desc=$desc")

        for (i in 0 until node.childCount) {
            printNodeTree(node.getChild(i), depth + 1)
        }
    }
}