package com.magenta.foodalrecipe.ui.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by ${User} on ${Date}
 */
class NavigationViewModel : ViewModel() {

    private lateinit var currentPos: MutableLiveData<Int>

    fun modifyPosition(pos: Int) {
        if (!::currentPos.isInitialized)
            currentPos = MutableLiveData()

        currentPos.value = pos
    }

    fun currentPosition(): Int {
        if (!::currentPos.isInitialized) {
            currentPos = MutableLiveData()
            currentPos.value = 0
        }
        return currentPos.value!!
    }


}