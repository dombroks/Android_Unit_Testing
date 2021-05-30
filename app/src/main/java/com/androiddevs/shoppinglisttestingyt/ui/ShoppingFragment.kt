package com.androiddevs.shoppinglisttestingyt.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.shoppinglisttestingyt.R
import dagger.hilt.EntryPoint

@EntryPoint
class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    lateinit var viewmodel: ShoppingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }
}