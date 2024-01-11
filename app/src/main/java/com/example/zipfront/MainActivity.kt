package com.example.zipfront

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.zipfront.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AmainBnv.setItemIconTintList(null);
        initBottomNavigation()
    }
    private fun initBottomNavigation() {

        bottomNavigationView = binding.AmainBnv

        supportFragmentManager
            .beginTransaction()
            .replace(binding.AmainFrame.id, FragmentHome())
            .commitAllowingStateLoss()

        bottomNavigationView.selectedItemId = R.id.homeFragment

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.propertyFragment -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentProperty()) // Replace with your fragment
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.homeFragment -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentHome())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.helpFragment -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentHome())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
    }
}