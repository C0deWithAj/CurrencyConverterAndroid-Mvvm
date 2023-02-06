package com.aj.currencycalculator.ui.currencyconverter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.aj.currencycalculator.R
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.databinding.FragmentCurrencyConverterBinding
import com.aj.currencycalculator.ui.base.BaseFragment
import com.aj.currencycalculator.util.extension.hideKeyboard
import com.aj.currencycalculator.util.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterFragment : BaseFragment() {

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
        binding.lastUpdateDateTime = ""
        binding.viewModel = viewModel
        addObservers()
        addDropDownListeners()
    }

    private fun addObservers() {
        viewModel.currencyList.observe(this.viewLifecycleOwner, currencyListObserver)
        viewModel.lastFetchDateTime.observe(this.viewLifecycleOwner) {
            it?.let { time ->
                binding.lastUpdateDateTime = time
            }
        }
    }

    private fun addDropDownListeners() {
        binding.dropdownFromCurrency.setOnItemClickListener { parent, _, position, _ ->
            if (position >= 0 && !binding.etFrom.text.isNullOrEmpty()) {
                binding.etFrom.hideKeyboard()
            }
        }

        binding.dropdownToCurrency.setOnItemClickListener { parent, _, position, _ ->
            if (position >= 0 && !binding.etFrom.text.isNullOrEmpty()) {
                binding.etTo.hideKeyboard()
            }
        }
    }

    private val currencyListObserver = Observer<ResultData<List<CurrencyRateUI>>> {
        when (it) {
            is ResultData.Loading -> {
                showProgressDialog()
            }

            is ResultData.Success -> {
                it.data?.let { _ ->
                    binding.currenciesList = it.data
                }
                hideProgressDialog()
            }

            is ResultData.Failed -> {
                hideProgressDialog()
                binding.parentLayout.showSnackBar(
                    it.message ?: "Unknown Error",
                    it.title ?: "Oh Snap!"
                )
            }

            is ResultData.Exception -> {
                hideProgressDialog()
                binding.parentLayout.showSnackBar(it.msg ?: "Unknown Error", "Oh Snap!")
            }

            else -> {
                context?.let { context ->
                    binding.parentLayout.showSnackBar(
                        context.getString(R.string.unknown_error),
                        context.getString(R.string.oh_snap)
                    )
                }
            }
        }
    }
}
