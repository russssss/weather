package com.android.weatherapp.features.weather

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R

class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val city: TextView
    val weather: TextView

    init {
        city = itemView.findViewById<TextView>(R.id.info)
        weather = itemView.findViewById<TextView>(R.id.weather)
    }
}