package com.aj.currencycalculator.ui.bindingadapter

import android.R
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.aj.currencycalculator.data.model.CurrencyRateUI

object BindingAdapters : BaseObservable() {

    @BindingAdapter("loadCurrencies")
    @JvmStatic
    fun loadCurrencies(txt: AutoCompleteTextView, currencyCode: List<CurrencyRateUI>?) {
        currencyCode?.map { it.code }?.let {
            txt.setAdapter(
                ArrayAdapter(
                    txt.context,
                    R.layout.simple_list_item_activated_1,
                    it
                )
            )
        }
    }
}