package com.dungle.android.aiactivassignment.presentation.flights.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.dungle.android.aiactivassignment.R
import com.dungle.android.aiactivassignment.common.Utils
import com.dungle.android.aiactivassignment.databinding.FlightItemBinding
import com.dungle.android.aiactivassignment.domain.model.Flight

class FlightAdapter(private val data: MutableList<Flight>) : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>(){

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        return FlightViewHolder(
            FlightItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = data.size

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: Flight, position: Int) {
        data.add(position, item)
        notifyItemInserted(position)
    }


    fun getFlight(position: Int) : Flight {
        return data[position]
    }

    class FlightViewHolder(private val itemBinding: FlightItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindView(flight: Flight) {
            itemBinding.tvName.text = flight.name
            itemBinding.tvFlightNumber.text = itemBinding.tvFlightNumber.context.getString(R.string.text_flight_number, flight.flightNumber.toString())
            itemBinding.tvLaunchDate.text = Utils.getDate(flight.dateUtc)
            itemBinding.tvLaunchTime.text = itemBinding.tvLaunchTime.context.getString(R.string.text_launch_time, Utils.getTime(flight.dateUtc))
        }
    }
}
