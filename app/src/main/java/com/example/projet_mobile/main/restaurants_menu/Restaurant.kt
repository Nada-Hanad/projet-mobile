package com.example.projet_mobile.main.restaurants_menu

data class MenuItem(val name: String, val photo: String, val price: Double, val description: String, val restaurantId: Int)
data class OrderItem(val name: String, val photo: String, val price: Double, val description: String, var quantity: Int, val note: String)
data class Restaurant(
    val _id: String,
    val name: String,
    val logo: String,
    val location: Location,
    val cuisineType: String,
    val averageRating: Double = 0.0,
    val reviewCount: Int = 0,
    val contact: Contact,
    val deliveryPrice: Double,
    val deliveryTime: Int,
    val menu: List<Menu>,
    val reviews: List<String>
)

data class Location(
    val address: String,
    val map: String?
)

data class Contact(
    val phone: String?,
    val email: String?,
    val socialMedia: String?
)

data class Menu(
    val picture: String,
    val itemName: String,
    val itemDescription: String,
    val price: Double,
    val restaurantId: String
)
