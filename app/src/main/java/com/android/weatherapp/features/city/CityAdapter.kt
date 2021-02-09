package com.android.weatherapp.features.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapp.R

class CityAdapter : RecyclerView.Adapter<CityHolder>() {

    var onItemClick: ((String) -> Unit)? = null
    var data = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        var v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.city_layout, parent, false)

        return CityHolder(v)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.city.text = data[position]
        holder.weather.text = data[position]

        holder.city.setOnClickListener {
            onItemClick?.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun update(data: ArrayList<String>) {
        this.data = data
        notifyDataSetChanged()
    }
}