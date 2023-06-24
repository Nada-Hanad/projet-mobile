package com.example.projet_mobile.main

import com.example.projet_mobile.main.restaurants_menu.Restaurant



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.NotificationItemBinding


data class Notification(
    val title: String,
    val des: String,

)

class NotifAdapter(
    val data: List<Notification>,
) : RecyclerView.Adapter<NotifAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.apply {
            /*val name:String,var logo:Int, var type:String, var rating:Double, var location: String,
            var  ReviewsNumber: Int, var phone: String );*/
            titreNotif.text = data[position].title

            decrNotif.text = data[position].des



        }





    }


    class MyViewHolder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root)

}



