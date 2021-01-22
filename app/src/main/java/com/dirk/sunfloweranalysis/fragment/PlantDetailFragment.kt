package com.dirk.sunfloweranalysis.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dirk.sunfloweranalysis.R
import com.dirk.sunfloweranalysis.data.Plant
import com.dirk.sunfloweranalysis.databinding.FragmentPlantDetailBinding
import com.dirk.sunfloweranalysis.viewmodels.PlantDetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// 注入点， 用来标记在哪里注入？  TODO Inject表示注入了什么
@AndroidEntryPoint
class PlantDetailFragment : Fragment(){

    //页面入参 ，根据xml布局自动生成
    private val args: PlantDetailFragmentArgs by navArgs()

    @Inject
    lateinit var plantDetailViewModelFactory: PlantDetailViewModel.AssistedFactory

    // 这里的闭包 需要 返回 ViewModelProvider.Factory
    // val plantDetailViewModel2 : PlantDetailViewModel by lazy{
    //     plantDetailViewModelFactory.create((args.plantId))
    // }

    // 天予不取 反受其咎 时至不行 反受其殃

    // 这里的闭包 需要 返回 ViewModelProvider.Factory  (通过ViewModel方式 相当于 包装模式，TODO 看一下具体增强了哪些功能)
    private val plantDetailViewModel : PlantDetailViewModel by viewModels{
        PlantDetailViewModel.provideFactory(plantDetailViewModelFactory,args.plantId)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantDetailBinding.inflate(inflater, container, false)
            .apply {
                // 这个数据 来源与xml中的 data 结点下面
                viewModel = plantDetailViewModel
                callback = object : Callback {
                    override fun add(plant: Plant?) {
                        //将数据插入到db
                        plant?.let {
                            hideAppBarFab(fab)
                            plantDetailViewModel.addPlantToGarden()
                            Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }

                galleryNav.setOnClickListener {
                    navigateToGallary()
                }

                var isToolbarShown = false

                plantDetailScrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the plant name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                })

                toolbar.setNavigationOnClickListener {
                    //TODO zfc 这是一个什么效果的Api， 其便利之处在于  1.是view的扩展属性  2.navigateUp的作用是什么？
                    view?.findNavController()?.navigateUp()
                }

                toolbar.setOnMenuItemClickListener { item ->
                    when(item.itemId){
                            R.id.action_share ->{
                                createShareIntent()
                                true
                            }

                            else -> false
                    }
                }
            }

        setHasOptionsMenu(true)

        return binding.root
    }



    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    //
    // This is adapted from Chris Banes' Stack Overflow answer: https://stackoverflow.com/a/41442923
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }



    private fun navigateToGallary(){
        plantDetailViewModel.plant.value?.let {plant ->
            //TODO 这里direction对象的生成应该比较奇妙
            val direction = PlantDetailFragmentDirections.actionPlantDetailFragmentToGalleryFragment(plant.name)
            findNavController().navigate(direction)
        }
    }

    private fun createShareIntent(){
        val shareTxt = plantDetailViewModel.plant.value.let {plant ->
            if(plant == null){
                ""
            } else {
                getString(R.string.share_text_plant,plant.name)
            }
        }
        //requireActivity TODO 学到了一个新API
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
                .setText(shareTxt)
                .setType("text/plain")
                .createChooserIntent()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

        startActivity(shareIntent)
    }

    interface Callback{
        fun add(plant: Plant?)
    }
}