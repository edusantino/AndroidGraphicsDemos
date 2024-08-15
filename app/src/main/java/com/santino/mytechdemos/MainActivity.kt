package com.santino.mytechdemos

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.santino.mytechdemos.screens.home.HomeFragment
import com.santino.mytechdemos.screens.onboarding.OnBoardingFragment


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        sharedPrefs = getSharedPreferences("MySettings", MODE_PRIVATE)

        if (!sharedPrefs.getBoolean(IS_ONBOARDED, false)) {
            val onBoardingFragment = OnBoardingFragment().apply {
                onOnboardingFinished = {
                    replaceFragment(HomeFragment())
                }
            }
            replaceFragment(onBoardingFragment)
        } else {
            homeFragment = HomeFragment()
            replaceFragment(homeFragment)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun replaceFragment(fragment: Fragment, addOnBackStack: Boolean? = null) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    companion object {
        const val IS_ONBOARDED = "is_onboarded"
    }
}