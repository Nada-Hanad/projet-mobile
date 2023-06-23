package com.example.projet_mobile.main.restaurants_menu.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.MenuItemItemBinding
import com.example.projet_mobile.main.restaurants_menu.MenuItem
import com.squareup.picasso.Picasso

class MenuItemAdapter(
    val data: List<MenuItem>,
    val onItemClick: (menuItem: MenuItem) -> Unit // Callback function for item click
) : RecyclerView.Adapter<MenuItemAdapter.MyMenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMenuViewHolder {
        return MyMenuViewHolder(MenuItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }




    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MenuItemAdapter.MyMenuViewHolder, position: Int) {




        holder.binding.apply {

            // Set data to views
            itemName.text = data[position].name
            itemPrice.text = data[position].price.toString() + "DA"
            itemDescription.text = data[position].description
            Picasso.get().load(data[position].photo).into(itemImage)



        }

        holder.itemView.setOnClickListener {
            // Invoke callback function and pass clicked restaurant data
            onItemClick.invoke(data[position])
        }



    }


    class MyMenuViewHolder(val binding: MenuItemItemBinding) : RecyclerView.ViewHolder(binding.root)

}



