package com.androiddevs.shoppinglisttestingyt.repositories

import com.androiddevs.shoppinglisttestingyt.other.*
import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItemDatabase
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayAPI
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import dagger.hilt.EntryPoint
import retrofit2.Response
import javax.annotation.Resource
import javax.inject.Inject


class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchImage(imageQuery: String): com.androiddevs.shoppinglisttestingyt.other.Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let com.androiddevs.shoppinglisttestingyt.other.Resource.success(it)
                } ?: com.androiddevs.shoppinglisttestingyt.other.Resource.error(
                    "An unknown error occured",
                    null
                )
            } else {
                com.androiddevs.shoppinglisttestingyt.other.Resource.error(
                    "An unknow error occured",
                    null
                )
            }
        } catch (e: Exception) {
            com.androiddevs.shoppinglisttestingyt.other.Resource.error(e.message.toString(), null)
        }
    }


}