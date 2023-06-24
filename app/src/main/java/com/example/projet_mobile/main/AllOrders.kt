package com.example.projet_mobile.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynavigation.retrofit.Endpoint
import com.example.projet_mobile.R
import com.example.projet_mobile.databinding.FragmentAllOrdersBinding
import com.example.projet_mobile.main.orders.OrderAdapter
import com.example.projet_mobile.main.restaurants_menu.MyModel

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllOrders : Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView

    private lateinit var binding: FragmentAllOrdersBinding

    lateinit var myModel: MyModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllOrdersBinding.inflate(layoutInflater)
        val view = binding.root


        binding.ordersCardsList.layoutManager = LinearLayoutManager(requireActivity())


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)
        progressBar = binding.pBar
        recyclerView = binding.ordersCardsList
        loadOrders();


    }

    fun loadOrders() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Une erreur s'est  ", Toast.LENGTH_SHORT).show()
            }
        }

        progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch(exceptionHandler) {
            try {
                val response = Endpoint.createEndpoint().getOrder(
                    myModel.user!!._id
                )


                if (response.isSuccessful && response.body() != null) {
                    val ordersResponse = response.body()!!
                    //print response


                    recyclerView.adapter = OrderAdapter( ordersResponse.toMutableList() )
                    progressBar.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                progressBar.visibility = View.INVISIBLE
                val errorMessage = "Une erreur s'est produite: ${e.message}"
                Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }




}