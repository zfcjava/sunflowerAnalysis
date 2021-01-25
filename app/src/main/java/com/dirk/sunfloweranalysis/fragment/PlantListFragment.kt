package com.dirk.sunfloweranalysis.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.dirk.sunfloweranalysis.R
import com.dirk.sunfloweranalysis.adapters.PlantAdapter
import com.dirk.sunfloweranalysis.databinding.FragmentPlantListBinding
import com.dirk.sunfloweranalysis.viewmodels.PlantListViewModel

class PlantListFragment : Fragment() {

    //TODO 这里为什么不需要 提供工厂了呢？
    val viewModel:PlantListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPlantListBinding.inflate(inflater, container, false)
        context?: return binding.root

        val adapter = PlantAdapter()
        binding.rclPlantList.adapter = adapter

        //如何更新数据化
        subscribeUi(adapter)
        setHasOptionsMenu(true)
        return binding.root
    }

    //TODO menu的追加流程
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    private fun subscribeUi(adapter: PlantAdapter) {
        viewModel.plants.observe(viewLifecycleOwner){ plants->
            adapter.submitList(plants)
        }
    }


    private fun updateData(){
        with(viewModel){
            if(isFiltered()){
                clearGrowZoneNumber()
            } else {
                setGrowZoneNumber(9)
            }
        }
    }
}