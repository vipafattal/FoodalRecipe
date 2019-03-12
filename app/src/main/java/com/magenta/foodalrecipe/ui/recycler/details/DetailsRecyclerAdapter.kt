package com.magenta.foodalrecipe.ui.recycler.details;

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.utils.extensions.inflater
import kotlinx.android.synthetic.main.item_details.view.*

/**
 * Created by ${User} on ${Date}
 */
class DetailsRecyclerAdapter(private val dataList: List<String>) :
    RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflater(R.layout.item_details)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        val ingredient = DetailsRecyclerHelper.formatIngredientItem(data)

        holder.itemView.apply {
            number_text_ingredients.text = ingredient.number
            if (ingredient.unit.isNotEmpty() && ingredient.unit.isNotBlank())
                unit_text_ingredients.text = ingredient.unit
            else
                unit_text_ingredients.visibility = View.GONE

            detail_text_ingredients.text = ingredient.detail
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}