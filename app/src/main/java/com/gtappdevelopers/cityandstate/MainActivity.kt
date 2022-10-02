package com.gtappdevelopers.cityandstate

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var loadingPB: ProgressBar
    lateinit var pinCodeEdt: TextInputEditText
    lateinit var getCityAndStateBtn: Button
    lateinit var cityTV: TextView
    lateinit var detailsRL: RelativeLayout
    lateinit var stateTV: TextView
    lateinit var postOfficeList: ArrayList<PostOfficeRVModal>
    lateinit var postOfficeRV: RecyclerView
    lateinit var postOfficeRVAdapter: PostOfficeRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingPB = findViewById(R.id.idPBLoading)
        pinCodeEdt = findViewById(R.id.idEdtPinCode)
        getCityAndStateBtn = findViewById(R.id.idBtnGetCity)
        cityTV = findViewById(R.id.idTVCity)
        detailsRL = findViewById(R.id.idRLDetails)
        stateTV = findViewById(R.id.idTVSTate)
        postOfficeRV = findViewById(R.id.idRVPostOffices)
        postOfficeList = ArrayList<PostOfficeRVModal>()
        postOfficeRVAdapter = PostOfficeRVAdapter(postOfficeList, this)
        postOfficeRV.adapter = postOfficeRVAdapter

        getCityAndStateBtn.setOnClickListener {
            if (pinCodeEdt.text.toString() != null) {
                getPinCodeDetails(pinCodeEdt.text.toString())
            } else {
                Toast.makeText(this, "Please enter your pin code..", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPinCodeDetails(pincode: String) {
        postOfficeList.clear()
        loadingPB.visibility = View.VISIBLE
        // request queue and initializing it.
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)

        var apiURL = "https://api.postalpincode.in/pincode/" + pincode


        val request = JsonArrayRequest(Request.Method.GET, apiURL, null, { response ->
            loadingPB.visibility = View.GONE
            detailsRL.visibility = View.VISIBLE
            val district = response.getJSONObject(0).getJSONArray("PostOffice").getJSONObject(0)
                .getString("District")
            val state = response.getJSONObject(0).getJSONArray("PostOffice").getJSONObject(0)
                .getString("State")


            cityTV.visibility = View.VISIBLE
            stateTV.visibility = View.VISIBLE

            val postOfficeArray = response.getJSONObject(0).getJSONArray("PostOffice")
            for (i in 0..postOfficeArray.length() - 1) {
                val postOfficeName = postOfficeArray.getJSONObject(i).getString("Name")
                val postOfficeBranch =
                    postOfficeArray.getJSONObject(i).optString("BranchType")
                val deliveryStatus = postOfficeArray.getJSONObject(i).optString("DeliveryStatus")
                val postOffice = PostOfficeRVModal(
                    postOfficeName,
                    postOfficeBranch,
                    deliveryStatus
                )
                postOfficeList.add(postOffice)
            }
            postOfficeRVAdapter.notifyDataSetChanged()

            cityTV.text = district
            stateTV.text = state

        }, { error ->
            loadingPB.visibility = View.GONE
            Toast.makeText(this@MainActivity, "Fail to get Bank Details", Toast.LENGTH_SHORT)
                .show()
        })

        // at last we are adding
        // our request to our queue.
        queue.add(request)

    }
}