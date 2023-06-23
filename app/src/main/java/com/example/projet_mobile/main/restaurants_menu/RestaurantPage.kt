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
import com.example.projet_mobile.R
import com.example.projet_mobile.databinding.FragmentHomeBinding
import com.example.projet_mobile.databinding.FragmentRestaurantPageBinding
import com.example.projet_mobile.main.restaurants_menu.menu.MenuItemAdapter
import com.squareup.picasso.Picasso

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
        binding.menuItemsRecyclerView.adapter = MenuItemAdapter(restaurant.menuItems)
        { menu ->

            myModel.selectedMenuItem = menu
            findNavController().navigate(R.id.action_go_to_menu_details)


        }

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



//        val location = binding.location
//        val phone = binding.phone
            val fbButton = binding.fbIcon
            val mailButton = binding.mailIcon
            val phoneButton = binding.phoneIcon


//
//        // Set data to views from the ViewModel
        ratingBar.setOnRatingBarChangeListener { _, ratingValue, _ ->
            Toast.makeText(requireContext(), "Rating: $ratingValue", Toast.LENGTH_SHORT).show()
        }
        myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)

        val restaurant = myModel.data[0]
        fbButton.setOnClickListener {
            // Open Facebook page
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.fb))
                startActivity(intent)
            }
            catch (e: Exception){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.fb))
                startActivity((intent))
            }
        }

        mailButton.setOnClickListener {
            // Open mail application
            try {
                val data = Uri.parse("mailto:${restaurant.email}")
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
                val data = Uri.parse("tel:${restaurant.phone}")
                val intent = Intent(Intent.ACTION_DIAL, data)
                startActivity(intent)
            }
            catch (e: Exception){
                print(e.message)
            }
        }
        name.text = restaurant.name
        type.text = restaurant.type
        rating.text = restaurant.rating.toString()
        if(restaurant.deliveryPrice == 0.0)
            delivery.text = "Free Delivery"
        else
            delivery.text = restaurant.deliveryPrice.toString() + "DA"
        time.text = restaurant.deliveryTime.toString() + " min"
//        location.text = restaurant.location
//        phone.text = restaurant.phone
        Picasso.get().load(restaurant.image).into(image);



        // Set click listener for the Facebook button
//        fbButton.setOnClickListener {
//            // Replace with the appropriate logic for visiting the Facebook page
//            // e.g. opening a webview or redirecting to the Facebook app
//            Toast.makeText(requireActivity(),
//                "Opening Facebook page for ${restaurant.name}"
//                , Toast.LENGTH_SHORT).show()
//        }
    }
}