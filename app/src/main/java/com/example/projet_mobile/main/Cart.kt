package com.example.projet_mobile.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projet_mobile.databinding.FragmentCartBinding
import com.example.projet_mobile.main.restaurants_menu.MyModel
import com.example.projet_mobile.main.restaurants_menu.OrderItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

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
            // Perform order validation logic here

            // Clear the cart content in local storage
            clearCartContentFromLocalStorage()

            // Update the UI
            cartContent.clear()
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.total.text = "Total: 0 DA"
        }
        binding.recyclerView.adapter = adapter

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