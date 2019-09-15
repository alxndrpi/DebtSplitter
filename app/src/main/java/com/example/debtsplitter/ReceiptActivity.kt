package com.example.debtsplitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_receipt.*
import org.json.JSONArray

class ReceiptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        val vList = findViewById<LinearLayout>(R.id.receipt_linear)
        receipt_cancel.setOnClickListener {
            finish()
        }

        val check_data = JSONArray(intent.getStringExtra("check_data"))
//        for (item: Item in check_data) {
//            Log.d("AASDASDASDASD", item.name)
//        }
        val inflater = layoutInflater
        for (i in 0 until check_data.length()) {
            val view = inflater.inflate(R.layout.check_item_card, vList, false)
            val item = check_data.getJSONObject(i)

            val item_name = view.findViewById<TextView>(R.id.item_name)
            val item_count = view.findViewById<TextView>(R.id.item_count)
            val item_price = view.findViewById<TextView>(R.id.item_price)

            item_name.setText(item.getString("name"))
            item_count.setText(item.getString("price"))
            item_price.setText(item.getString("quantity"))

            vList.addView(view)
        }
    }
}
