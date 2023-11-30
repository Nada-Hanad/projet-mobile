package com.example.mynavigation.retrofit

import android.widget.Toast
import com.example.projet_mobile.main.restaurants_menu.OrderItem
import com.example.projet_mobile.main.restaurants_menu.Restaurant
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface Endpoint {



    @GET("api/restaurants")
    suspend fun getAllRestaurants(): Response<List<Restaurant>>
    @POST("api/restaurants/{id}/rate")
    suspend fun rateRestaurant(
        @Path("id") id: String,
        @Body ratingRequest: RatingRequest
    ): Response<Restaurant>

    @POST("api/restaurants/{id}/reviews")
    suspend fun submitReview(
        @Path("id") id: String,
        @Body reviewRequest: ReviewRequest
    ): Response<Restaurant>
    @POST("api/orders")
    suspend fun submitOrder(
        @Body orderRequest: OrderRequest
    ): Response<OrderResponse>
    @GET("api/orders/{id}")
    suspend fun getOrder(
        @Path("id") id: String
    ): Response<List<OrderByUserResponse>>
    @POST("api/users/register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Response<AuthResponse>
    @POST("api/users/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<AuthResponse>

    data class LoginRequest (
        val email: String,
        val password: String
 )

    data class RegisterRequest(
        val name: String,
        val email: String,
        val password: String,
        val address: String,
        val phone: String
    )
    data class User(
        val name: String,
        val email: String,
        val password: String,
        val address: String,
        val phone: String,
        val notifications: List<Any>,
        val orders: List<Any>,
        val isLoggedIn: Boolean,
        val _id: String,
    )

    data class AuthResponse(
        val message: String,
        val user: User
    )


    companion object {
        private const val BASE_URL = "https://870e-105-235-129-170.eu.ngrok.io"
        private var endpoint: Endpoint? = null

        fun createEndpoint(): Endpoint {
            if (endpoint == null) {
                synchronized(this) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    endpoint = retrofit.create(Endpoint::class.java)
                    println(endpoint) ;
                }
            }
            return endpoint!!
        }
    }
}

data class OrderRequest (
    val items: List<OrderItem>,
    val totalAmount: Double,
    val deliveryAddress: String,
    val deliveryNote: String,
    val status : String = "Pending",
    val userId : String,
)


data class OrderResponse(
    val deliveryPhoneNumber: String,
    val deliveryName: String,
    val ETA: Int,
    val order: Order
)

data class Order(
    val items: List<OrderItem>,
    val totalAmount: Double,
    val deliveryAddress: String,
    val status: String,

    )



data class RatingRequest(
    val rating: Double
)

data class ReviewRequest(
    val review: String
)

data class OrderByUserResponse(
    val order: Order,
    val deliveryPhoneNumber: String,
    val deliveryName: String,
    val ETA: Int
)



data class Item(
    val name: String,
    val photo: String,
    val quantity: Int,
    val price: Double,
    val description: String,
    val note: String,
)
