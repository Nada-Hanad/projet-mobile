package com.example.projet_mobile.main.orders

import android.graphics.Color
import com.example.projet_mobile.main.restaurants_menu.Restaurant


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynavigation.retrofit.OrderByUserResponse
import com.example.mynavigation.retrofit.OrderResponse
import com.example.projet_mobile.databinding.OrderItemCardBinding
import com.squareup.picasso.Picasso

class OrderAdapter(
    val data: List<OrderByUserResponse>,
) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(OrderItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.apply {

            val order = data[position].order
            val deliveryInfo = data[position]


            dName.text = data[position].deliveryName
            dAddress.text = data[position].order.deliveryAddress
            dPhone.text = data[position].deliveryPhoneNumber
            dPrice.text = data[position].order.totalAmount.toString() + " DA"
            dStatus.text = data[position].order.status
            val statusColor = when (order.status) {
                "Pending" -> Color.GRAY
                "Being Prepared" -> Color.YELLOW
                "On Route" -> Color.GREEN
                "Delivered" -> Color.BLUE
                else -> Color.BLACK
            }
            dStatus.setTextColor(statusColor)





        }



    }


    class MyViewHolder(val binding: OrderItemCardBinding) : RecyclerView.ViewHolder(binding.root)

}



