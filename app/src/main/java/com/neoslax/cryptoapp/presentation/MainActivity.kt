package com.neoslax.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neoslax.cryptoapp.R
import com.neoslax.cryptoapp.databinding.MainActivityBinding
import com.neoslax.cryptoapp.di.DaggerApplicationComponent


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragment()

    }

    private fun setupFragment() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, CoinPriceListFragment.newInstance())
            .commit()


    }
}