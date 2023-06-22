package com.example.projet_mobile.slider

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.projet_mobile.R


class IntroSliderAdapter(private val introSlider: List<IntroSlider>):
    RecyclerView.Adapter<IntroSliderAdapter.IntroSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_container, parent, false)
        return IntroSlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        val introSlider = introSlider[position]
        holder.bind(introSlider)
    }

    override fun getItemCount(): Int {
        return introSlider.size
    }
    inner class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val introTitle = view.findViewById<TextView>(R.id.introTitle)
        private val introDescription = view.findViewById<TextView>(R.id.introDescription)
        private val iconIntro = view.findViewById<ImageView>(R.id.iconIntro)

        fun bind(introSlider: IntroSlider){
            introTitle.text = introSlider.title
            introDescription.text = introSlider.description
            iconIntro.setImageResource(introSlider.icon)
        }

    }

}