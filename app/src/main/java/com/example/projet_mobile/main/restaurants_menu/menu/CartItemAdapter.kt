package com.example.projet_mobile.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.CartItemBinding
import com.example.projet_mobile.main.restaurants_menu.OrderItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso

class CartItemAdapter(
    val context: Context,
    val data: MutableList<OrderItem>,
    val onItemClick: (menuItem: OrderItem) -> Unit,
    val onItemDelete: (menuItem: OrderItem) -> Unit,
    val onQuantityChange: () -> Unit
) : RecyclerView.Adapter<CartItemAdapter.MyCartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        return MyCartViewHolder(
            CartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        val orderItem = data[position]

        holder.binding.apply {
            itemName.text = orderItem.name
            itemPrice.text = "${orderItem.price * orderItem.quantity} DA"
            itemQuantity.text = orderItem.quantity.toString()
            Picasso.get().load(orderItem.photo).into(itemImage)

            minusButton.setOnClickListener {
                if (orderItem.quantity > 1) {
                    orderItem.quantity--
                    itemQuantity.text = orderItem.quantity.toString()
                    itemPrice.text = "${orderItem.price * orderItem.quantity} DA"
                    saveCartContentToLocalStorage()
                    onQuantityChange.invoke()
                }
            }

            plusButton.setOnClickListener {
                orderItem.quantity++
                itemQuantity.text = orderItem.quantity.toString()
                itemPrice.text = "${orderItem.price * orderItem.quantity} DA"
                saveCartContentToLocalStorage()
                onQuantityChange.invoke()
            }

            deleteButton.setOnClickListener {
                onItemDelete.invoke(orderItem)
                onQuantityChange.invoke()

            }
        }
    }

    class MyCartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    private fun saveCartContentToLocalStorage() {
        val gson = Gson()
        val json = gson.toJson(data)

        val sharedPreferences = context.getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("cartContent", json)
        editor.apply()
    }

    private fun loadCartContentFromLocalStorage(): MutableList<OrderItem> {
        val sharedPreferences = context.getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("cartContent", null)
        val gson = Gson()
        val itemType = object : TypeToken<MutableList<OrderItem>>() {}.type

        return gson.fromJson(json, itemType) ?: mutableListOf()
    }
}
