package com.example.expensetracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ActivityMainBinding
import com.example.expensetracker.db.TransactionsDatabase
import com.example.expensetracker.repository.TransactionsRepository
import com.example.expensetracker.viewmodel.TransactionsViewModel
import com.example.expensetracker.viewmodel.TransactionsViewModelProvFactory
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var viewModel: TransactionsViewModel
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = TransactionsRepository(TransactionsDatabase.getInstance(this))

        val viewModelProviderFactory = TransactionsViewModelProvFactory(repository, application)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[TransactionsViewModel::class.java]
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        drawerLayout = binding.drawerLayout
        navView = binding.drawerNavView
        navView.setupWithNavController(navController)
        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(
            navController.graph, drawerLayout
        )

        // this to make navigate up when on different fragment
        binding.toolbar.setupWithNavController(navController, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)

        lifecycleScope.launchWhenCreated {

            if (viewModel.readUIPreference("night_mode") == true) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}