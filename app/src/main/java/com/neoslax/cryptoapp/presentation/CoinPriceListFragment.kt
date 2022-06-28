package com.neoslax.cryptoapp.presentation

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neoslax.cryptoapp.R
import com.neoslax.cryptoapp.databinding.FragmentCoinListBinding
import com.neoslax.cryptoapp.domain.entities.CoinInfo
import com.neoslax.cryptoapp.presentation.adapter.CoinInfoAdapter
import javax.inject.Inject


class CoinPriceListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[CoinViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CoinApp).component
    }

    private var _binding: FragmentCoinListBinding? = null
    private val binding: FragmentCoinListBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinListBinding == null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    private fun setupFragment() {

        val isLandMode =
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> true
                else -> false
            }
        val adapter = CoinInfoAdapter(requireContext())

        with(binding) {

            rvPriceListActivity.adapter = adapter
            rvPriceListActivity.itemAnimator = null
        }
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coin: CoinInfo) {
                Log.d("TEST_COIN_INFO", "Click on  ${coin.fromSymbol}")

                launchDetailFragment(isLandMode, coin.fromSymbol)
            }
        }

        viewModel.coinInfoList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.progressCircular.visibility = View.GONE
                binding.errorBottomBar.animate().apply {
                    translationY(100f)
                    duration = 300
                    withEndAction { binding.errorBottomBar.visibility = View.GONE }
                    start()
                }
                binding.rvPriceListActivity.animate().apply {
                    scaleX(1f)
                    scaleY(1f)
                    duration = 300
                    start()
                }

            }
            adapter.submitList(it)
        }

    }

    private fun launchDetailFragment(isLandMode: Boolean, fSym: String) {
        if (isLandMode) {
            with(requireActivity().supportFragmentManager) {
                popBackStack()
                beginTransaction()
                    .replace(R.id.main_container_detailed, CoinDetailFragment.newInstance(fSym))
                    .addToBackStack(null).commit()
            }
        } else {
            requireActivity().supportFragmentManager.popBackStack()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, CoinDetailFragment.newInstance(fSym))
                .addToBackStack(null).commit()

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(): Fragment {
            return CoinPriceListFragment()
        }
    }
}