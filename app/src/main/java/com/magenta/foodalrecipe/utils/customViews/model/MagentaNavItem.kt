package com.magenta.foodalrecipe.utils.customViews.model

import com.magenta.foodalrecipe.utils.customViews.model.ItemsModel


class MagentaNavItem(itemsModel: MutableList<ItemsModel>) {

    private val MIN_Items = 3
    private val MAX_Items = 5
    internal var itemsArray= itemsModel
    private set

     /* fun addItem(itemModel: ItemsModel){
         ItemsModel()
         itemsArray.add(itemModel)
     }*/
}