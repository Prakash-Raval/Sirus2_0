package com.example.sirus20.onborading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.sirus20.R
import com.example.sirus20.databinding.FragmentOnBoardingBinding
import com.example.sirus20.onborading.adapter.ViewPagerAdapter

class OnBoardingFragment : Fragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var sliderAdapter: ViewPagerAdapter
    private var currentPosition = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_on_boarding,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sliderAdapter = ViewPagerAdapter(requireContext())
        binding.slider.adapter = sliderAdapter
        //methods
        buttonClicks()
        binding.isVisible = true
        binding.slider.addOnPageChangeListener(changeListener)
        binding.dot2.attachTo(binding.slider)
    }

    /*
    * navigation button clicks
    * */
    private fun buttonClicks() {
        binding.skipBtn.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToIntroductionFragment2())
        }
        binding.nextBtn.setOnClickListener {
            binding.slider.currentItem = currentPosition.plus(1)
        }
        binding.getStartedBtn.setOnClickListener {

            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToIntroductionFragment2())

        }
    }

    /*
    * manage buttons visibility
    * */
    private val changeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                when (position) {
                    0 -> binding.isVisible = true
                    1 -> binding.isVisible = true
                    2 -> binding.isVisible = true

                    else -> {
                        val bottom =
                            AnimationUtils.loadAnimation(requireContext(), R.anim.bootm_anim)
                        binding.getStartedBtn.animation = bottom
                        binding.isVisible = false
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        }

}