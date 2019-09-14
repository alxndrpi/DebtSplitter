package com.example.debtsplitter

import android.util.Log
import android.view.MenuItem

fun navigationListener(item: MenuItem ): Boolean {
    when (item.itemId) {
        R.id.nav_friends -> {
            Log.i("nav", "friends")
            return true
        }
        R.id.nav_events -> {
            Log.i("nav", "events")
            return true
        }
        R.id.nav_settings -> {
            Log.i("nav", "settings")
            return true
        }
    }
    return false
}