package com.example.projet_mobile.main.restaurants_menu

import androidx.lifecycle.ViewModel
import com.example.mynavigation.retrofit.OrderResponse

class MyModel:ViewModel() {
    val data = mutableListOf<Restaurant>()
    var selectedMenuItem:Menu? = null
    var cartContent = mutableListOf<OrderItem>()
    var deliveryInfo : OrderResponse? = null
    var ordersCard : MutableList<OrderResponse> = mutableListOf()
}




