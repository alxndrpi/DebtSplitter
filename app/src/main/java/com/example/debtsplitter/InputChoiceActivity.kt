package com.example.debtsplitter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_input_choice.*
import kotlinx.android.synthetic.main.navigation_layout.*
import org.json.JSONArray
import java.io.Serializable

data class Item(
    val name: String,
    val price: Int,
    val quantity: Int
)

class InputChoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_choice)

        val bottomNavigation: BottomNavigationView = navigationView
        bottomNavigation.setOnNavigationItemSelectedListener(::navigationListener)

        manualInputButton.setOnClickListener {

            var jsonString = Gson().toJson(listOf(Item("test", 2, 1), Item("tes", 2, 1)))
            val intent = Intent(this, ReceiptActivity::class.java)
            intent.putExtra("check_data", jsonString)
            startActivity(intent)
        }

        scanQRButton.setOnClickListener {
            val intent = Intent(this@InputChoiceActivity, ScanActivity::class.java)
            startActivity(intent)
        }

    }
}
