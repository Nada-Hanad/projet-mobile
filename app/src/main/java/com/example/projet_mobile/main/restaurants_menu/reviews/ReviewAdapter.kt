package com.example.projet_mobile.main.restaurants_menu.reviews


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile.databinding.ReviewItemBinding


class ReviewAdapter(
    var data: List<String>,
) : RecyclerView.Adapter<ReviewAdapter.MyMenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMenuViewHolder {
        return MyMenuViewHolder(ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }




    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ReviewAdapter.MyMenuViewHolder, position: Int) {


        holder.binding.apply {
            reviewItem.text = data[position]
        }
    }

    fun updateReviews(newReviews: List<String>) {
        data = newReviews
        notifyDataSetChanged()
    }
    class MyMenuViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)

}




