package com.dirk.sunfloweranalysis.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dirk.sunfloweranalysis.fragment.GalleryFragment
import com.dirk.sunfloweranalysis.fragment.PlantDetailFragment
import java.lang.IndexOutOfBoundsException

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1


class SunflowerPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {


    //创建一个map用来存储fragment， 要想懒加载，就需要使用闭包了
    private val createFragments = mapOf<Int, () -> Fragment>(
        MY_GARDEN_PAGE_INDEX to { GalleryFragment() },
        PLANT_LIST_PAGE_INDEX to { PlantDetailFragment() }
    )


    override fun getItemCount() = createFragments.size

    override fun createFragment(position: Int) = createFragments[position]?.invoke() ?: throw IndexOutOfBoundsException()
}