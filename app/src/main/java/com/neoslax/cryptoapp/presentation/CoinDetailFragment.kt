package com.neoslax.cryptoapp.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neoslax.cryptoapp.databinding.CoinDetailFragmentBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
    }

    private var _binding: CoinDetailFragmentBinding? = null
    private val binding: CoinDetailFragmentBinding
        get() = _binding ?: throw RuntimeException("CoinDetailFragmentBinding == null")

    private val component by lazy {
        (requireActivity().application as CoinApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CoinDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fSym = requireArguments().getString(FROM_SYMBOL)
            ?: throw RuntimeException("fSym in onViewCreated == null")
        setupFragment(fSym)
    }


    private fun setupFragment(fSym: String) {
        viewModel.getCoinDetailInfo(fSym).observe(viewLifecycleOwner) {
            Log.d("COIN_DETAIL_INFO", it.toString())
            with(binding) {
                Picasso.get().load(it.imageUrl).into(ivDetailInfo)
                tvFromCurrencyName.text = it.fromSymbol
                tvToCurrencyName.text = it.toSymbol
                tvCurrency.text = it.price.toString()
                tvCurrencyMax.text = it.highDay.toString()
                tvCurrencyMin.text = it.lowDay.toString()
                tvLastTransaction.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FROM_SYMBOL = "fSym"

        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }

}
