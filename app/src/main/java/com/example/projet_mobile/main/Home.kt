package com.example.projet_mobile.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynavigation.retrofit.Endpoint
import com.example.projet_mobile.R
import com.example.projet_mobile.databinding.FragmentHomeBinding
import com.example.projet_mobile.main.restaurants_menu.MyModel
import com.example.projet_mobile.main.restaurants_menu.RestaurentAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Home : Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView

    private lateinit var binding: FragmentHomeBinding

    lateinit var myModel: MyModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root


        binding.ordersList.layoutManager = LinearLayoutManager(requireActivity())
       /* binding.recyclerView.adapter = RestaurentAdapter(loadRestaurants())

        { restaurant ->
            // Handle item click and pass data to Fragment2
            // Set the data in MyModel
            //always add it in position 0
            myModel.data.add(0,restaurant)

            findNavController().navigate(R.id.action_homeFragment_to_restaurantFragment)
        }
*/


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel = ViewModelProvider(requireActivity()).get(MyModel::class.java)
        progressBar = binding.progressBar
        recyclerView = binding.ordersList
        loadRestaurants();


    }

/*    fun loadData():List<Restaurant> {
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
    }*/
    fun loadRestaurants() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Une erreur s'est  ", Toast.LENGTH_SHORT).show()
            }
        }

        progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch(exceptionHandler) {
            try {
                val response = Endpoint.createEndpoint().getAllRestaurants()
                println("restaurantsResponse")


                if (response.isSuccessful && response.body() != null) {
                    val restaurantsResponse = response.body()!!
                    //print response

                    recyclerView.adapter = RestaurentAdapter( restaurantsResponse.toMutableList() ) { restaurant ->
                            // Handle item click and pass data to Fragment2
                            // Set the data in MyModel
                            //always add it in position 0
                            print(restaurant) ;
                            myModel.data.add(0,restaurant)

                            findNavController().navigate(R.id.action_homeFragment_to_restaurantFragment)
                         }
                    progressBar.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                progressBar.visibility = View.INVISIBLE
                val errorMessage = "Une erreur s'est produite: ${e.message}"
                Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}