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