package com.example.debtsplitter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_input_choice.*
import kotlinx.android.synthetic.main.content_input_choice.*
import kotlinx.android.synthetic.main.navigation_layout.*


class InputChoiceActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_choice)

        val bottomNavigation: BottomNavigationView = navigationView
        bottomNavigation.setOnNavigationItemSelectedListener(::navigationListener)
    }
}
