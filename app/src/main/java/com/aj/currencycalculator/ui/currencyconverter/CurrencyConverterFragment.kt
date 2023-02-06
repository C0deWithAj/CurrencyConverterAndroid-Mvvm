package com.aj.currencycalculator.ui.currencyconverter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.aj.currencycalculator.R
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.databinding.FragmentCurrencyConverterBinding
import com.aj.currencycalculator.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterFragment : Fragment() {

    private val viewModel: CurrencyConverterViewModel by viewModels()
    private lateinit var binding: FragmentCurrencyConverterBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCurrencyConverterBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.currenciesList = ArrayList<CurrencyRateUI>()
        addObservers()
        addDropDownListeners()
        viewModel.loadList()
    }

    private fun addObservers() {
        viewModel.currencyList.observe(this.viewLifecycleOwner, currencyListObserver)
    }

    private fun addDropDownListeners() {
        binding.dropdownFromCurrency.setOnItemClickListener { parent, _, position, _ ->
            if (position >= 0 && !binding.etFrom.text.isNullOrEmpty()) {
                binding.etFrom.hideKeyboard()
                //Fetch Data
            }
        }

        binding.dropdownToCurrency.setOnItemClickListener { parent, _, position, _ ->
            if (position >= 0 && !binding.etFrom.text.isNullOrEmpty()) {
                binding.etTo.hideKeyboard()
                //Fetch Data
            }
        }
    }

    private val currencyListObserver = Observer<ResultData<List<CurrencyRateUI>>> {
        when (it) {
            is ResultData.Loading -> {

            }

            is ResultData.Success -> {
                it.data?.let { _ ->
                    binding.currenciesList = it.data
                }
            }

            is ResultData.Failed -> {

            }

            is ResultData.Exception -> {

            }

            else -> {

            }
        }
    }


}
