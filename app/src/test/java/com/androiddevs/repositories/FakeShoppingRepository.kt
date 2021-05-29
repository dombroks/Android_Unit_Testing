package com.androiddevs.repositories

import androidx.lifecycle.MutableLiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItem

class FakeShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice  = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false


    fun setShouldReturnNetworkError(value : Boolean){
        shouldReturnNetworkError = value
    }
}