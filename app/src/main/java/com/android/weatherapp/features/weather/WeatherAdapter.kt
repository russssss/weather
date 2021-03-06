package com.android.weatherapp.features.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R
import kotlin.math.ceil

class WeatherAdapter: RecyclerView.Adapter<WeatherHolder>() {

    var onItemClick: ((Weather) -> Unit)? = null
    var data = ArrayList<Weather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        var v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.weather_item_layout, parent, false)

        return WeatherHolder(v)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.city.text = data[position].date
        holder.weather.text = ceil(data[position].temp ?: 0.0).toInt().toString()

        holder.city.setOnClickListener {
            onItemClick?.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun update(data: ArrayList<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }
}