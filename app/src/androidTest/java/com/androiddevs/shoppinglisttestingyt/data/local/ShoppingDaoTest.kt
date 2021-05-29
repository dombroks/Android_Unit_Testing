package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }


    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("iPhone", 100, 1f, "none", id = 1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(shoppingItem in allShoppingItems).isTrue()
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("iPhone", 100, 1f, "none", id = 1)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(shoppingItem in allShoppingItems).isFalse()

    }

    @Test
    fun observeTotalPriceItem()  = runBlockingTest {

        val shoppingItem = ShoppingItem("iPhone", 100, 1f, "none", id = 1)
        val shoppingItem2 = ShoppingItem("Samsung", 50, 1f, "none", id = 2)
        dao.insertShoppingItem(shoppingItem)
        dao.insertShoppingItem(shoppingItem2)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()
        var sum = 0f
        allShoppingItems.forEach {
            sum += it.price * it.amount
        }

        assertThat(dao.observeTotalPrice().getOrAwaitValue()).isEqualTo(sum)



    }
}