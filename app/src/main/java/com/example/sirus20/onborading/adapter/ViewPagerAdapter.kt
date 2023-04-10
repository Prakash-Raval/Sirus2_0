package com.example.sirus20.onborading.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.sirus20.R

class ViewPagerAdapter(context: Context) : PagerAdapter() {
    private var context: Context
    private var layoutInflater: LayoutInflater

    init {
        this.context = context
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private val slides = arrayOf(
        R.drawable.search_place,
        R.drawable.call,
        R.drawable.add_missing_place,
        R.drawable.sit_back_and_relax
    )
    private val slidingHeader = arrayOf(
        R.string.first_slide_title,
        R.string.second_slide_title,
        R.string.third_slide_title,
        R.string.fourth_slide_title
    )
    private val slidingDesc = arrayOf(
        R.string.first_slide_desc,
        R.string.second_slide_desc,
        R.string.third_slide_desc,
        R.string.fourth_slide_desc
    )

    override fun getCount(): Int {
        return slidingHeader.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = layoutInflater.inflate(R.layout.item_silder_layout, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.imgScreenSlide)
        val textTitle = itemView.findViewById<TextView>(R.id.txtText1)
        val textDesc = itemView.findViewById<TextView>(R.id.txtText2)
        container.addView(itemView)
        imageView.setImageResource(slides[position])
        textTitle.setText(slidingHeader[position])
        textDesc.setText(slidingDesc[position])

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}