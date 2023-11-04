package com.project.myspending.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.project.myspending.R
import com.project.myspending.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navHostController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost_container) as NavHostFragment
        navHostController = navHostFragment.navController
    }
}
