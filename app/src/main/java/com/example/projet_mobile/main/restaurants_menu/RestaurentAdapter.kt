package com.example.projet_mobile.main.restaurants_menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.RestaurantItemLayoutBinding
import com.squareup.picasso.Picasso

class RestaurentAdapter(
    val data: List<Restaurant>,
    val onItemClick: (restaurant: Restaurant) -> Unit // Callback function for item click
) : RecyclerView.Adapter<RestaurentAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RestaurantItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.apply {
            /*val name:String,var logo:Int, var type:String, var rating:Double, var location: String,
            var  ReviewsNumber: Int, var phone: String );*/
            name.text = data[position].name
            Picasso.get().load(data[position].logo).into(image)

            Picasso.get().load(data[position].image).into(image);

            rating.text = data[position].rating.toString()
            type.text = data[position].type



        }

        holder.itemView.setOnClickListener {
            // Invoke callback function and pass clicked restaurant data
            onItemClick.invoke(data[position])
        }



    }


    class MyViewHolder(val binding: RestaurantItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}



