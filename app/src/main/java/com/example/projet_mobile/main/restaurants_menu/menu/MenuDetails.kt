package com.example.projet_mobile.main.restaurants_menu.menu

import android.content.Context

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.projet_mobile.R
import com.example.projet_mobile.databinding.FragmentMenuDetailsBinding
import com.example.projet_mobile.main.restaurants_menu.MyModel
import com.example.projet_mobile.main.restaurants_menu.OrderItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso

class MenuDetails : Fragment() {
    private var quantity: Int = 1

    private lateinit var binding: FragmentMenuDetailsBinding
    private lateinit var myModel: MyModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)

        val menu = myModel.selectedMenuItem
        binding.name.text = menu?.itemName
        binding.price.text = "${menu?.price} $"
        binding.description.text = menu?.itemDescription
        binding.quantity.text = "1"
        binding.totalPrice.text = "Total Price: ${menu?.price} $"
        Picasso.get().load(menu?.picture).into(binding.image)

        binding.addToCartButton.setOnClickListener {
            val orderItem = OrderItem(
                menu!!.itemName,
                menu!!.picture,
                menu!!.price,
                menu!!.itemDescription,
                quantity,
                binding.note.text.toString()
            )

            val cartContent = loadCartContentFromLocalStorage()

            // Check if the menu item already exists in the cart
            val existingItem = cartContent.find { it.name == orderItem.name }
            if (existingItem != null) {
                Toast.makeText(requireActivity(), "Item already in cart", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            cartContent.add(orderItem)
            saveCartContentToLocalStorage(cartContent)

            Toast.makeText(requireActivity(), "Added to cart", Toast.LENGTH_SHORT).show()
        }


        binding.minus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantity()
            }
        }

        binding.plus.setOnClickListener {
            quantity++
            updateQuantity()
        }
    }

    private fun updateQuantity() {
        binding.quantity.text = "$quantity"
        val menu = myModel.selectedMenuItem
        val totalPrice = menu?.price?.times(quantity)
        binding.totalPrice.text = "Total Price: $totalPrice $"
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
}
