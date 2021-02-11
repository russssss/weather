package com.android.weatherapp.features.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R
import com.android.weatherapp.features.weather.WeatherModel
import kotlin.math.ceil

class CityAdapter : RecyclerView.Adapter<CityHolder>() {

    var onItemClick: ((WeatherModel) -> Unit)? = null
    var data = ArrayList<WeatherModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        var v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.city_item_layout, parent, false)

        return CityHolder(v)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.city.text = data[position].city
        data[position]?.weather?.get(0)
            ?.let { holder.weather.text = ceil(it.temp ?: 0.0).toInt().toString() }

        holder.city.setOnClickListener {
            onItemClick?.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun update(data: ArrayList<WeatherModel>) {
        this.data = data
        notifyDataSetChanged()
    }
}