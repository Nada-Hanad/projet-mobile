package com.example.projet_mobile.main.restaurants_menu

import androidx.lifecycle.ViewModel

class MyModel:ViewModel() {
    val data = mutableListOf<Restaurant>()
    var selectedMenuItem:MenuItem? = null
    var cartContent = mutableListOf<OrderItem>()


}