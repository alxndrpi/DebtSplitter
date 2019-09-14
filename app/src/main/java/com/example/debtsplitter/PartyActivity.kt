package com.example.debtsplitter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_party.*

class PartyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party)


        new_party_button.setOnClickListener {
            val intent = Intent(this, InputChoiceActivity::class.java)
            startActivity(intent)
        }
    }
}
