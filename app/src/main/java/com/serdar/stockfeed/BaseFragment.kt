package com.serdar.stockfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VDB: ViewDataBinding>(@LayoutRes val layoutId: Int): Fragment(layoutId) {

    lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<VDB>(inflater, layoutId, container, false).apply {
            binding = this
            binding.lifecycleOwner = this@BaseFragment
            init()
        }.root
    }

    protected abstract fun init()
}