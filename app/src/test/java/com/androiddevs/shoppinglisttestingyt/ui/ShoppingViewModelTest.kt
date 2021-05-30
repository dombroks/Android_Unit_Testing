package com.androiddevs.shoppinglisttestingyt.ui

import androidx.lifecycle.ViewModelProvider
import com.androiddevs.getOrAwaitValue
import com.androiddevs.repositories.FakeShoppingRepository
import com.androiddevs.shoppinglisttestingyt.other.Status
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class ShoppingViewModelTest {

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun insertShoppingItemsWithEmptyFieldsReturnsError() {
        viewModel.insertShoppingItem("", "300", "35.7")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled())?.isEqualTo(Status.ERROR)
    }

}