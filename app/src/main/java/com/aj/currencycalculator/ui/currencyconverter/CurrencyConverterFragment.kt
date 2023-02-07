package com.aj.currencycalculator.ui.currencyconverter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.aj.currencycalculator.R
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.databinding.FragmentCurrencyConverterBinding
import com.aj.currencycalculator.ui.base.BaseFragment
import com.aj.currencycalculator.util.extension.guardLet
import com.aj.currencycalculator.util.extension.hideKeyboard
import com.aj.currencycalculator.util.extension.showSnackBar
import com.aj.currencycalculator.util.extension.toTwoDecimalWithComma
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
        initBindings()
        addObservers()
        addViewListeners()
    }

    private fun addObservers() {
        viewModel.currencyList.observe(this.viewLifecycleOwner, currencyListObserver)
        viewModel.lastFetchDateTime.observe(this.viewLifecycleOwner) {
            it?.let { time ->
                binding.lastUpdateDateTime = time
            }
        }
        viewModel.convertedCurrency.observe(this.viewLifecycleOwner, convertedCurrencyObserver)
    }

    private fun initBindings() {
        binding.currenciesList = ArrayList<CurrencyRateUI>()
        binding.lastUpdateDateTime = ""
        binding.viewModel = viewModel
    }

    private fun addViewListeners() {
        binding.etFrom.doAfterTextChanged {
            it?.let { editable ->
                if (editable.toString().isEmpty()) {
                    binding.etFrom.append("1")
                } else {
                    onCurrencyChangedEvent()
                }
            }
        }

        binding.dropdownFromCurrency.setOnItemClickListener { parent, _, position, _ ->
            binding.etFrom.hideKeyboard()
            if (position >= 0 && !binding.etFrom.text.isNullOrEmpty() && !binding.dropdownToCurrency.text.isNullOrEmpty()) {
                onCurrencyChangedEvent()
            }
        }

        binding.dropdownToCurrency.setOnItemClickListener { parent, _, position, _ ->
            binding.etTo.hideKeyboard()
            if (position >= 0 && !binding.etFrom.text.isNullOrEmpty() && !binding.dropdownFromCurrency.text.isNullOrEmpty()) {
                onCurrencyChangedEvent()
            }
        }

        binding.btnSwap.setOnClickListener {
            val toCurrencyOld = binding.dropdownToCurrency.text
            binding.dropdownToCurrency.text = binding.dropdownFromCurrency.text
            binding.dropdownFromCurrency.text = toCurrencyOld
            onCurrencyChangedEvent()
        }
    }

    private fun onCurrencyChangedEvent() {
        guardLet(
            binding.etFrom.text.toString(),
            binding.dropdownFromCurrency.text.toString(),
            binding.dropdownToCurrency.text.toString()
        ) {
            return
        }
        viewModel.onCurrencyInputChanged(
            binding.etFrom.text.toString(),
            binding.dropdownFromCurrency.text.toString(),
            binding.dropdownToCurrency.text.toString()
        )
    }

    private val currencyListObserver = Observer<ResultData<List<CurrencyRateUI>>> {
        when (it) {
            is ResultData.Loading -> {
                showProgressDialog()
            }

            is ResultData.Success -> {
                resetSelections()
                it.data?.let { _ ->
                    binding.currenciesList = it.data
                }
                hideProgressDialog()
            }

            is ResultData.Failed -> {
                hideProgressDialog()
                showError(it.title, msg = it.message)
            }

            is ResultData.Exception -> {
                hideProgressDialog()
                showError(msg = it.msg)
            }

            else -> {
                hideProgressDialog()
                showError()
            }
        }
    }

    private val convertedCurrencyObserver = Observer<ResultData<Double>> {
        when (it) {
            is ResultData.Success -> {
                it.data?.let { converted ->
                    binding.etTo.setText(converted.toTwoDecimalWithComma())
                }
            }

            is ResultData.Failed -> {
                showError(it.title, msg = it.message)
            }

            is ResultData.Exception -> {
                showError(msg = it.msg)
            }

            else -> {
                showError()
            }
        }
    }

    private fun showError(
        title: String? = context?.getString(R.string.unknown_error),
        msg: String? = context?.getString(R.string.oh_snap)
    ) {
        if (!title.isNullOrEmpty() && !msg.isNullOrEmpty()) {
            binding.parentLayout.showSnackBar(
                msg,
                title
            )
        }
    }

    private fun resetSelections() {
        binding.etFrom.setText("1")
        binding.etTo.setText("")
        binding.dropdownToCurrency.setText("")
        binding.dropdownFromCurrency.setText("")
    }

}
