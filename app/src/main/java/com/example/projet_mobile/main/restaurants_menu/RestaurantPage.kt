package com.example.projet_mobile.main.restaurants_menu

import android.content.Intent
import android.net.Uri
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
import com.example.mynavigation.retrofit.RatingRequest
import com.example.mynavigation.retrofit.ReviewRequest
import com.example.projet_mobile.R
import com.example.projet_mobile.databinding.FragmentHomeBinding
import com.example.projet_mobile.databinding.FragmentRestaurantPageBinding
import com.example.projet_mobile.main.restaurants_menu.menu.MenuItemAdapter
import com.example.projet_mobile.main.restaurants_menu.reviews.ReviewAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RestaurantPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestaurantPage : Fragment() {
    private lateinit var binding: FragmentRestaurantPageBinding
    lateinit var myModel: MyModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantPageBinding.inflate(layoutInflater)
        val view = binding.root

        myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)
        val restaurant = myModel.data[0]
        binding.menuItemsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.menuItemsRecyclerView.adapter = MenuItemAdapter(restaurant.menu)

        { menu ->

            myModel.selectedMenuItem = menu
            findNavController().navigate(R.id.action_go_to_menu_details)


        }
        binding.reviewList.layoutManager = LinearLayoutManager(requireActivity())
        binding.reviewList.adapter = ReviewAdapter(restaurant.reviews)
        return view
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        // Initialize views from the layout
        val image = binding.image
        val name = binding.name
        val type = binding.type
        val rating = binding.rating
        val delivery = binding.delivery
        val time = binding.time
        val ratingBar = binding.ratingBar
        val submitReviewButton = binding.submitReviewButton



//        val location = binding.location
//        val phone = binding.phone
            val fbButton = binding.fbIcon
            val mailButton = binding.mailIcon
            val phoneButton = binding.phoneIcon
            val mapButton = binding.mapButton


        val restaurant = myModel.data[0]
//        // Set data to views from the ViewModel
        submitReviewButton.setOnClickListener {
            val restaurantId = restaurant._id

            val reviewEditText = binding.reviewEditText
            val reviewText = reviewEditText.text.toString().trim()
            val reviewRequest = ReviewRequest(reviewText)

            // Check if the review text is not empty
            if (reviewText.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        // Call the suspend function within the coroutine context
                        val response = withContext(Dispatchers.IO) {
                            Endpoint.createEndpoint().submitReview(restaurantId, reviewRequest)
                        }

                        if (response.isSuccessful) {
                            val updatedRestaurant = response.body()
                            Toast.makeText(requireContext(), "Submitted successfully", Toast.LENGTH_SHORT).show()

                            // Update the restaurant object in the view model
                            myModel.data[0] = updatedRestaurant!!

                            // Update the reviews data in the current fragment
                            val reviewAdapter = binding.reviewList.adapter as? ReviewAdapter
                            reviewAdapter?.updateReviews(updatedRestaurant!!.reviews)

                            // Optionally, you can update other views that display the restaurant data

                            rating.text = String.format("%.1f", updatedRestaurant?.averageRating)
                        } else {
                            Toast.makeText(requireContext(), "An error has occurred!", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "An error has occurred!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please enter a review", Toast.LENGTH_SHORT).show()
            }
        }



        ratingBar.setOnRatingBarChangeListener { _, ratingValue, _ ->
            val restaurantId = restaurant._id
            val rating = ratingValue
            val ratingRequest = RatingRequest(rating.toDouble())



            // Launch a coroutine
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Call the suspend function within the coroutine context
                    val response = withContext(Dispatchers.IO) {
                        Endpoint.createEndpoint().rateRestaurant(restaurantId, ratingRequest)
                    }

                    if (response.isSuccessful) {
                        val updatedRestaurant = response.body()
                        Toast.makeText(requireContext(), "Submitted successfully", Toast.LENGTH_SHORT).show()

                        // Handle the updated restaurant object
                    } else {
                        Toast.makeText(requireContext(), "An error has accured!", Toast.LENGTH_SHORT).show()

                        // Handle the error
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "An error has accured!", Toast.LENGTH_SHORT).show()

                }
            }


        }




        myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)


        fbButton.setOnClickListener {
            // Open Facebook page
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.contact.socialMedia))
                startActivity(intent)
            }
            catch (e: Exception){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.contact.socialMedia))
                startActivity((intent))
            }
        }
        mapButton.setOnClickListener {
            val restaurantName = restaurant.name
            val mapsUrl = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(restaurantName)}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
            startActivity(intent)
        }
        mailButton.setOnClickListener {
            // Open mail application
            try {
                val data = Uri.parse("mailto:${restaurant.contact.email}")
                val intent = Intent(Intent.ACTION_SENDTO, data)
                startActivity(intent)
            }
            catch (e: Exception){
                print(e.message)
            }
        }

        phoneButton.setOnClickListener {
            // Open phone dialer
            try {
                val data = Uri.parse("tel:${restaurant.contact.phone}")
                val intent = Intent(Intent.ACTION_DIAL, data)
                startActivity(intent)
            }
            catch (e: Exception){
                print(e.message)
            }
        }
        name.text = restaurant.name
        type.text = restaurant.cuisineType
        rating.text = String.format("%.1f", restaurant.averageRating)
        if(restaurant.deliveryPrice == 0.0)
            delivery.text = "Free Delivery"
        else
            delivery.text = restaurant.deliveryPrice.toString() + "DA"
        time.text = restaurant.deliveryTime.toString() + " min"

        Picasso.get().load(restaurant.logo).into(image);


    }
}