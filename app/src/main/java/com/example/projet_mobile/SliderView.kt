package com.example.projet_mobile




import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.projet_mobile.slider.IntroSlider
import com.example.projet_mobile.slider.IntroSliderAdapter


class SliderView : AppCompatActivity() {

    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlider(
                "Welcome",
                "It’s a pleasure to meet you. We are excited that you’re here so let’s get started!",
                R.drawable.img_1
            ),
            IntroSlider(
                "All your favorites",
                "Order from the best local restaurants with easy, on-demand delivery.",
                R.drawable.img_3
            ),
            IntroSlider(
                "Free delivery offers",
                "Free delivery for new customers via Apple Pay and others payment methods.",
                R.drawable.img_4
            ),
            IntroSlider(
                "Choose your food",
                "Easily find your type of food craving and you’ll get delivery in wide range.",
                R.drawable.img_5
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ViewPager2?>(R.id.viewPager2).adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicators(0)
        findViewById<ViewPager2>(R.id.viewPager2).registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicators(position)
            }
        })
        val btnNavigate: Button = findViewById(R.id.started)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupIndicators(){
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams=
            LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            findViewById<LinearLayout>(R.id.scrollView2).addView(indicators[i])
        }
    }
    private fun setCurrentIndicators(index: Int) {
        val childCount = findViewById<LinearLayout>(R.id.scrollView2).childCount
        for (i in 0 until childCount) {
            val imageView = findViewById<LinearLayout>(R.id.scrollView2)[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat. getDrawable( applicationContext,
                        R.drawable. indicator_active
                    )
                )
            }else {
                imageView.setImageDrawable(
                    ContextCompat. getDrawable( applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}