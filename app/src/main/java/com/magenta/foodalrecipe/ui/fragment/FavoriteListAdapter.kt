package com.magenta.foodalrecipe.ui.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.ui.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.ui.recycler.recipes.RecipeViewHolder
import kotlinx.android.synthetic.main.item_recipe.view.*

/**
 * Created by ${User} on ${Date}
 */
class FavoriteListAdapter(
    private val dataList: MutableList<Recipe>,
    private val viewModel: RecipeRepositoriesViewModel
) :
    RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            viewModel,
            parent,
            R.layout.item_recipe,
            false
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = dataList[position]
        holder.bindRecipeData(recipe)

        holder.itemView.recipeFavAnim.setOnClickListener {
            viewModel.updateFavState(false, recipe.id)
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }

    }


}