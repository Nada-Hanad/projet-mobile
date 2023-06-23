package com.example.projet_mobile.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projet_mobile.R
import com.example.projet_mobile.databinding.FragmentHomeBinding
import com.example.projet_mobile.main.restaurants_menu.MenuItem
import com.example.projet_mobile.main.restaurants_menu.MyModel
import com.example.projet_mobile.main.restaurants_menu.Restaurant
import com.example.projet_mobile.main.restaurants_menu.RestaurentAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding

    lateinit var myModel: MyModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root


        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = RestaurentAdapter(loadData())

        { restaurant ->
            // Handle item click and pass data to Fragment2
            // Set the data in MyModel
            //always add it in position 0
            myModel.data.add(0,restaurant)

            findNavController().navigate(R.id.action_homeFragment_to_restaurantFragment)
        }



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)
    }

    fun loadData():List<Restaurant> {
        val data = mutableListOf<Restaurant>()

        val menuItems1 = ArrayList<MenuItem>()
        menuItems1.add(MenuItem("Menu 1", "https://images.unsplash.com/photo-1560611588-163f295eb145?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8cmVzdGF1cmVudCUyMGZvb2R8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60", 10.99, "Delicious dish with special ingredients", 1))
        menuItems1.add(MenuItem("Menu 2", "https://images.unsplash.com/photo-1495147466023-ac5c588e2e94?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fHJlc3RhdXJlbnQlMjBmb29kfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60", 12.99, "Exotic flavors from around the world",1))
        menuItems1.add(MenuItem("Menu 3", "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Zm9vZHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60", 8.99, "Classic comfort food with a twist",1))
        data.add(Restaurant(1, "Restaurant 1", "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80", "Indian", 5.0, "Somewhere", 200, "0540202601", "https://www.facebook.com/", "https://images.unsplash.com/photo-1523145149804-5d8e0c044753?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8cmVzdGF1cmVudHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60",0.00, 20, menuItems1, "jn_hanad@esi.dz"))

        // Restaurant 2 with menu items
        val menuItems2 = ArrayList<MenuItem>()
        menuItems2.add(MenuItem("Menu 1", "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8Zm9vZHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60", 9.99, "Savory and spicy dishes for the adventurous",2))
        menuItems2.add(MenuItem("Menu 2", "https://images.unsplash.com/photo-1467003909585-2f8a72700288?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTR8fGZvb2R8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60", 11.99, "Mouthwatering seafood with a burst of flavors",2))
        menuItems2.add(MenuItem("Menu 3", "https://images.unsplash.com/photo-1493770348161-369560ae357d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTd8fGZvb2R8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60", 7.99, "Vegetarian options with fresh and organic ingredients",2))
        data.add(Restaurant(2, "Restaurant 2", "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80", "Indian", 5.0, "Somewhere", 200, "0540202601", "https://www.facebook.com/", "https://images.unsplash.com/photo-1480796927426-f609979314bd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8cmVzdGF1cmVudHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60",10.00,15, menuItems2, "jn_hanad@esi.dz"))


        return data
    }
}