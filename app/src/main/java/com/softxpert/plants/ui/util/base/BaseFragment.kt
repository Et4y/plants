package com.softxpert.plants.ui.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.softxpert.plants.databinding.CommonToolbarBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {


    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        receiveData()
        handleClicks()
        handleToolbar()
    }


    open fun handleClicks() {}

    open fun receiveData() {}


    open fun handleToolbar() {}

    fun CommonToolbarBinding.init(title: String) {
        this.tvToolbarTitle.text = title
        this.ivBack.setOnClickListener { findNavController().popBackStack() }
        this.tvToolbarTitle.setOnClickListener { ivBack.performClick() }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}