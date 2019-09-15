package com.example.debtsplitter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.json.JSONObject
import java.io.Serializable

class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)                // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        Log.i("QRSCAN", rawResult.text)

        val rawParams = rawResult.text.split('&').map {
            it.split('=').zipWithNext().first()
        }.toMap()
        val fnsUrl = "https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*/fss/${rawParams["fn"]}/tickets/${rawParams["i"]}?fiscalSign=${rawParams["fp"]}&sendToEmail=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = object : JsonObjectRequest(
            Method.GET, fnsUrl, null,
            Response.Listener { response ->  successResponseHandler(response) },
            Response.ErrorListener { response -> Log.e("QRSCAN", response.toString()) }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Basic Kzc5MTcyMzk1MTgyOjU4MDIwNQ=="
                headers["device-id"] = ""
                headers["device-os"] = ""
                return headers
            }
        }

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

        onBackPressed()

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }

    fun successResponseHandler(response: JSONObject) {
        Log.i("QRSCAN",response.toString(4))
        val intent = Intent(this, ReceiptActivity::class.java)
        val receiptItems = response.getJSONObject("document").getJSONObject("receipt").getJSONArray("items")
        intent.putExtra("check_data", receiptItems.toString())
        startActivity(intent)
    }
}
