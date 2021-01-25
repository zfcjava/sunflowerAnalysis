package com.dirk.sunfloweranalysis.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dirk.sunfloweranalysis.data.Plant
import com.dirk.sunfloweranalysis.databinding.ListItemPlantBinding
import com.dirk.sunfloweranalysis.fragment.HomeViewPagerFragmentDirections

class PlantAdapter() : ListAdapter<Plant, RecyclerView.ViewHolder>(PlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(
                ListItemPlantBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as PlantViewHolder).bind(plant)
    }


    /**
     * 入参一个Binding 用来提取 其中的View
     */
    class PlantViewHolder(val binding: ListItemPlantBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.setClicker {
                binding.plant?.let { plant ->
                    navigationToPlant(plant,it)
                }
            }
        }

        private fun navigationToPlant(plant: Plant, view: View) {
            val direction = HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                    plant.plantId
            )

            view.findNavController().navigate(direction)
        }

        fun bind(item: Plant) {
            binding.apply {
                plant = item
                //TODO 这句话的含义 有点？？？？？？
                executePendingBindings()
            }
        }
    }


}


/**
 * Item 比较工具类 TODO
 */
private class PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {

    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.plantId == newItem.plantId
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }

}