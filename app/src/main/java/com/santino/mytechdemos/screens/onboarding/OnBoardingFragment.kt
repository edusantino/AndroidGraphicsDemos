package com.santino.mytechdemos.screens.onboarding

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.santino.mytechdemos.databinding.OnboardingFragmentBinding

class OnBoardingFragment : Fragment() {

    var onOnboardingFinished: (() -> Unit)? = null

    private lateinit var binding: OnboardingFragmentBinding
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OnboardingFragmentBinding.inflate(inflater)

        sharedPrefs = requireActivity().getSharedPreferences("MySettings", MODE_PRIVATE)

        binding.btNext.setOnClickListener {
            sharedPrefs.edit().putBoolean("IS_ONBOARDED", true).apply()
            onOnboardingFinished?.invoke()
        }

        return binding.root
    }
}