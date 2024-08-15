package com.santino.mytechdemos.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.santino.mytechdemos.MainActivity
import com.santino.mytechdemos.databinding.HomeFragmentBinding
import com.santino.mytechdemos.demos.DemoFragment

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater)
        val mainActivity: MainActivity? = (requireActivity() as? MainActivity)

        binding.btDemo1.setOnClickListener {
            mainActivity?.replaceFragment(DemoFragment(demoNum = 1))
        }
        binding.btDemo2.setOnClickListener {
            mainActivity?.replaceFragment(DemoFragment(demoNum = 2))
        }
        binding.btDemo3.setOnClickListener {
            mainActivity?.replaceFragment(DemoFragment(demoNum = 3))
        }

        return binding.root
    }
}