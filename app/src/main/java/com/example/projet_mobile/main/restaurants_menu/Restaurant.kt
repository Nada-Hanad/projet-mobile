package com.example.projet_mobile.main.restaurants_menu

data class MenuItem(val name: String, val photo: String, val price: Double, val description: String, val restaurantId: Int)
data class OrderItem(val name: String, val photo: String, val price: Double, val description: String, var quantity: Int, val restaurantId: Int, val note: String)
data class Restaurant(
    val id: Int,
    val name: String,
    var logo: String,
    var type: String,
    var rating: Double,
    var location: String,
    val reviewsNumber: Int,
    var phone: String,
    var fb: String,
    var image: String,
    var deliveryPrice: Double,
    var deliveryTime: Int,
    val menuItems: ArrayList<MenuItem>,
    val email : String
)
