package com.example.debtsplitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONArray

class ReceiptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        val check_data = JSONArray(intent.getStringExtra("check_data"))
//        for (item: Item in check_data) {
//            Log.d("AASDASDASDASD", item.name)
//        }
        for (i in 0 until check_data.length()){
            var item = check_data.getJSONObject(i)
            Log.d("RECEIPT", item.getString("name"))
        }
    }
}
