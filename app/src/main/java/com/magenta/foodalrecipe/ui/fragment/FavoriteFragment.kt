package com.magenta.foodalrecipe.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.framework.Injection
import com.magenta.foodalrecipe.ui.ViewModel.RecipeRepositoriesViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.*


/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteFragment : BaseFragment() {

    override val viewRes: Int = R.layout.fragment_favorite
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(RecipeRepositoriesViewModel::class.java)

        coroutineScope.launch {

            val data = withContext(Dispatchers.IO) {
                mainViewModel.getFavoritedList(true)
            }
            if (!data.isEmpty()) {
                favoriteRV.adapter = FavoriteListAdapter(data, mainViewModel)
                favoriteRV.scrollRegister()
            } else
                parentActivity.showEmptyList(true)
        }
    }

    override fun onResume() {
        super.onResume()
        showNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancelChildren()
    }

    companion object {
        const val TAG = "Favorite-Fragment"
    }
}
