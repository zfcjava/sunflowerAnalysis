package com.dirk.sunfloweranalysis.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dirk.sunfloweranalysis.R
import com.dirk.sunfloweranalysis.adapters.MY_GARDEN_PAGE_INDEX
import com.dirk.sunfloweranalysis.adapters.PLANT_LIST_PAGE_INDEX
import com.dirk.sunfloweranalysis.adapters.SunflowerPagerAdapter
import com.dirk.sunfloweranalysis.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeViewPagerFragment : Fragment(){

    //onCreateView 基本原理不能少
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //ViewBinding 将view中的id 变为了binding中的属性
        val binding = FragmentViewPagerBinding.inflate(inflater,container,false)
        val tabs = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = SunflowerPagerAdapter(this)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()


        return binding.root
    }


    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector
            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title)
            PLANT_LIST_PAGE_INDEX -> getString(R.string.plant_list_title)
            else -> null
        }
    }
}