package com.santino.mytechdemos.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.santino.mytechdemos.databinding.DemoFragmentBinding

class DemoFragment(val demoNum: Int = 1) : Fragment() {

    private lateinit var binding: DemoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DemoFragmentBinding.inflate(inflater)

        setupDemo()
        return binding.root
    }

    private fun setupDemo() {
        when (demoNum) {
            1 -> binding.icDemo1.root.isVisible = true
            2 -> binding.icDemo2.root.isVisible = true
            3 -> binding.icDemo3.root.isVisible = true
        }
    }
}