package com.aj.currencycalculator.ui.currencydetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.aj.currencycalculator.R
import com.aj.currencycalculator.databinding.RowPopularCurrencyBinding
import com.aj.currencycalculator.ui.base.BaseListAdapter
import com.aj.currencycalculator.ui.model.ConvertedConversionUI
import com.aj.currencycalculator.ui.model.CurrencyUI

class PopularCurrencyAdapter(
    private val baseCurrency: CurrencyUI,
) : BaseListAdapter<ConvertedConversionUI, RowPopularCurrencyBinding>(diffCallback = diffCallback) {
    override fun createBinding(parent: ViewGroup, viewType: Int): RowPopularCurrencyBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_popular_currency,
            parent,
            false
        )
    }

    override fun bind(
        binding: RowPopularCurrencyBinding,
        item: ConvertedConversionUI,
        position: Int
    ) {
        binding.baseCurrency = baseCurrency
        binding.convertedCurrency = item
    }
}

val diffCallback: DiffUtil.ItemCallback<ConvertedConversionUI> =
    object : DiffUtil.ItemCallback<ConvertedConversionUI>() {
        override fun areItemsTheSame(
            oldItem: ConvertedConversionUI,
            newItem: ConvertedConversionUI
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: ConvertedConversionUI,
            newItem: ConvertedConversionUI
        ): Boolean {
            return false
        }
    }

