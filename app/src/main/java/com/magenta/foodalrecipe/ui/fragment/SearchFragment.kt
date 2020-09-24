package com.magenta.foodalrecipe.ui.fragment


import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.framework.Injection
import com.magenta.foodalrecipe.ui.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.ui.recycler.recipes.RecipesAdapter
import com.magenta.foodalrecipe.utils.commen.OFFLINE_SEARCH
import com.magenta.foodalrecipe.utils.commen.SharedPrefererance
import com.magenta.foodalrecipe.utils.commen.onBackPressedFragment
import com.magenta.foodalrecipe.utils.extensions.*
import kotlinx.android.synthetic.main.bottom_search_bar.*
import kotlinx.android.synthetic.main.bottom_search_bar.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_loading_animation.*


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : BaseFragment(), View.OnClickListener , onBackPressedFragment {

    override val viewRes: Int = R.layout.fragment_search

    private val currentState: MutableLiveData<Int> = MutableLiveData()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var mainViewModel: RecipeRepositoriesViewModel
    private var srcKey = ""
    private lateinit var adapter: RecipesAdapter
    private var isErrorOccurred = false
    private val sharedPrefs = SharedPrefererance.instance
    private val searchOffline = sharedPrefs.getBoolean(OFFLINE_SEARCH, true)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideNavigation()
        activity?.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        mainViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(RecipeRepositoriesViewModel::class.java)

        bottomSheetBehavior =
            BottomSheetBehavior.from(bottom_sheet_search)

        init()
    }

    private fun init() {

        bottomSliderCallback()

        touch_outside.setOnClickListener(this)
        peek_view_bottom_search.setOnClickListener(this)

        initGroupChipListener()

        onInputTextSubmit()

        onResultReceived()

        onErrorReceived()

        currentState.observer(this) {
            if (it != 0)
                state_icon.state_icon.setBackgroundResource(it)
            else
                state_icon.state_icon.setBackgroundResource(R.drawable.ic_search)
        }
    }

    private fun initGroupChipListener() {
        val elevation = context?.resources!!.getDimension(R.dimen.item_elevation)

        chip_group_search.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.chip_ranking) {
                chip_ranking.checked(elevation)
                chip_trending.unChecked()
            } else {
                chip_trending.checked(elevation)
                chip_ranking.unChecked()
            }
        }
    }

    private fun onInputTextSubmit() {
        search_text_input.onSubmit { searchKeyword ->
            initRecyclerView()
            closeBottomBar()
            search_title_text.text = """Result for "$searchKeyword" """
            srcKey = searchKeyword
            isErrorOccurred = false
            val sortBy = if (chip_ranking.isChecked) 'r' else 't'
            mainViewModel.getRecipes(srcKey, true, sortBy)
            loadingAnimation.visibility = View.VISIBLE
            currentState.postValue(R.drawable.ic_wifi)
        }
    }

    private fun onResultReceived() {
        mainViewModel.recipes.observer(this) {
            loadingAnimation.visibility = View.GONE
            adapter.submitList(it)
        }
    }

    private fun onErrorReceived() {
        mainViewModel.networkErrors.observer(this) {
            if (searchOffline) {
                if (mainViewModel.recipes.value == null || mainViewModel.recipes.value!!.size == 0) {
                    isErrorOccurred = true
                    val sortBy = if (chip_ranking.isChecked) 'r' else 't'
                    mainViewModel.getRecipes("%$srcKey%", false, sortBy)
                    currentState.postValue(R.drawable.ic_cloud)
                    initRecyclerView()
                }
            } else {
                requireContext().errorNotifier("Error occurred", it)
                currentState.postValue(R.drawable.ic_error)
            }
        }
    }

    private fun initRecyclerView() {
        adapter = RecipesAdapter(mainViewModel)
        searchRV.adapter = adapter
    }


    private fun bottomSliderCallback() {
        currentState.postValue(0)
        val bottomSliderCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    touch_outside.visibility = View.VISIBLE
                    showKeyboard()
                    if (currentState.value != R.drawable.ic_down)
                        state_icon.tag = currentState.value
                    currentState.postValue(R.drawable.ic_down)
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    touch_outside.visibility = View.INVISIBLE
                    if (currentState.value == R.drawable.ic_down)
                        currentState.postValue(state_icon.tag as Int)
                    hideKeyboard()
                }
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(bottomSliderCallback)
    }

    private fun showBottomBar() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun closeBottomBar() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }


    override fun onClick(v: View) {
        when (v.id) {

            R.id.peek_view_bottom_search -> {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
                    showBottomBar()
                else
                    closeBottomBar()
            }

            R.id.touch_outside -> {
                closeBottomBar()
            }

        }
    }

    override fun onBackPressed() {
        parentActivity.supportFragmentManager.popBackStack()
    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized)
            searchRV.adapter = adapter
    }

    companion object {
        const val TAG = "SEARCH-FRAGMENT"
    }

}
