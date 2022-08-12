package com.dungle.android.aiactivassignment.presentation.flights.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dungle.android.aiactivassignment.databinding.FragmentFlightsBinding
import com.dungle.android.aiactivassignment.domain.model.Flight
import com.dungle.android.aiactivassignment.presentation.flights.FlightsEvent
import com.dungle.android.aiactivassignment.presentation.flights.FlightsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightsFragment : Fragment() {
    private val flightsViewModel: FlightsViewModel by viewModels()
    private var _binding: FragmentFlightsBinding? = null
    private val binding get() = _binding!!
    private lateinit var flightAdapter: FlightAdapter
    private val data: MutableList<Flight> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        flightsViewModel.onEvent(FlightsEvent.Refresh)
        flightsViewModel.state.observe(viewLifecycleOwner) {
            showLoading(it.isLoading)

            if (it.error.isNotEmpty()) {
                showErrorMessage(it.error)
            }

            if (it.flights.isNotEmpty() || it.isRefreshing) {
                data.clear()
                data.addAll(it.flights)
                flightAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showLoading(isLoading: Boolean?) {
        if (isLoading == true) {
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun initUI() {
        binding.etSearch.addTextChangedListener {
            flightsViewModel.onEvent(FlightsEvent.OnSearchQueryChange(it.toString()))
        }
        flightAdapter = FlightAdapter(data)
        binding.rvFlights.apply {
            adapter = flightAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val simpleItemTouchCallback =
            object : SwipeToDeleteCallback(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (direction == ItemTouchHelper.LEFT) {
                        val flight = flightAdapter.getFlight(position)
                        flightsViewModel.onEvent(FlightsEvent.OnItemRemoveOrRestore(true, flight))
                        flightAdapter.removeItem(position)
                        val snack =
                            view?.let { Snackbar.make(it, "Item deleted", Snackbar.LENGTH_LONG) }
                        snack?.setAction("Undo") {
                            flightsViewModel.onEvent(FlightsEvent.OnItemRemoveOrRestore(false, flight))
                            flightAdapter.restoreItem(flight, position)
                            binding.rvFlights.scrollToPosition(position)
                        }
                        snack?.show()
                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvFlights)
    }
}