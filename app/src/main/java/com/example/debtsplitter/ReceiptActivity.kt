package com.example.debtsplitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ReceiptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        val check_data = getIntent().getStringExtra("check_data") as List<Item>
        for (item: Item in check_data) {
            Log.d("AASDASDASDASD", item.name)
        }
    }
}
