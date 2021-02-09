package com.android.weatherapp.features.citylist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R
import com.android.weatherapp.features.citydetails.CityDetailsActivity

class CityListActivity : AppCompatActivity() {

    lateinit var cityViewModel: CityViewModel
    lateinit var cityAdapter: CityAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.city_list_layout)

        initUI()
        init()
    }

    private fun initUI() {
        recyclerView = findViewById(R.id.city_list)
        cityAdapter = CityAdapter()
        recyclerView.adapter = cityAdapter

        cityAdapter.update(ArrayList())

        cityAdapter.onItemClick = { contact ->
            startActivity(Intent(this, CityDetailsActivity::class.java))
        }
    }

    private fun init() {

        val mock = mutableListOf<String>("Moscow", "Spb")
        cityAdapter.update(mock as ArrayList<String>)
    }


}