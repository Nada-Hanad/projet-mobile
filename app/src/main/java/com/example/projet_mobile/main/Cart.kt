package com.example.projet_mobile.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynavigation.retrofit.Endpoint
import com.example.mynavigation.retrofit.Order
import com.example.mynavigation.retrofit.OrderRequest
import com.example.projet_mobile.R
import com.example.projet_mobile.databinding.FragmentCartBinding
import com.example.projet_mobile.main.restaurants_menu.MyModel
import com.example.projet_mobile.main.restaurants_menu.OrderItem
import com.example.projet_mobile.main.restaurants_menu.reviews.ReviewAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Cart : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var myModel: MyModel
    private lateinit var adapter: CartItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)
        binding.ordersList.layoutManager = LinearLayoutManager(requireActivity())

        val cartContent = loadCartContentFromLocalStorage()
        adapter = CartItemAdapter(
            requireContext(),
            cartContent,
            { cartItem ->
                // Item click logic here if needed
            },
            { cartItem ->
                // Delete item logic here
                cartContent.remove(cartItem)
                adapter.notifyDataSetChanged()
                updateTotal()
                saveCartContentToLocalStorage(cartContent)
            },
            {
                // Quantity change logic here
                updateTotal()
                saveCartContentToLocalStorage(cartContent)
            }
        )

        binding.validateOrderButton.setOnClickListener {
            val total = cartContent.sumOf { it.price * it.quantity }
            val deliveryAddress = binding.etDeliveryAddress.text.toString().trim()
            val deliveryNote = binding.orderNotes.text.toString().trim()
            val items = loadCartContentFromLocalStorage()
            val req = OrderRequest(
                items,
                total,
                deliveryAddress,
                deliveryNote,
                "Pending",
                myModel.user!!._id
            )
            if (deliveryAddress.isNotEmpty()) {

                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        // Call the suspend function within the coroutine context
                        val response = withContext(Dispatchers.IO) {
                            Endpoint.createEndpoint().submitOrder(req)
                        }

                        if (response.isSuccessful) {
                            val deliveryInfo = response.body()
                            Toast.makeText(requireContext(), "Order Placed", Toast.LENGTH_SHORT).show()

                            // Update the restaurant object in the view model
                            myModel.deliveryInfo = deliveryInfo!!
                            clearCartContentFromLocalStorage()

                            // Update the UI
                            cartContent.clear()
                            binding.ordersList.adapter?.notifyDataSetChanged()
                            binding.total.text = "Total: 0 DA"
                            binding.etDeliveryAddress.setText("")
                            binding.orderNotes.setText("")


                            findNavController().navigate(R.id.action_navigation_cart_to_order)


                        } else {
                            Toast.makeText(requireContext(), "An error has occurred!", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "An error has occurred!", Toast.LENGTH_SHORT).show()
                    }
                }

            }else{
                Toast.makeText(requireContext(), "Please enter a delivery address", Toast.LENGTH_SHORT).show()
            }






        }
        binding.ordersList.adapter = adapter

        updateTotal()
    }

    private fun updateTotal() {
        val cartContent = loadCartContentFromLocalStorage()
        val total = cartContent.sumOf { it.price * it.quantity }
        binding.total.text = "Total: "+ String.format("%.2f", total) + " DA"
    }

    private fun saveCartContentToLocalStorage(cartContent: List<OrderItem>) {
        val gson = Gson()
        val json = gson.toJson(cartContent)

        val sharedPreferences = requireContext().getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("cartContent", json)
        editor.apply()
    }

    private fun loadCartContentFromLocalStorage(): MutableList<OrderItem> {
        val sharedPreferences = requireContext().getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("cartContent", null)
        val gson = Gson()
        val itemType = object : TypeToken<MutableList<OrderItem>>() {}.type

        return gson.fromJson(json, itemType) ?: mutableListOf()
    }
    private fun clearCartContentFromLocalStorage() {
        val sharedPreferences = requireContext().getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("cartContent")
        editor.apply()
    }
}