package com.aj.currencycalculator.ui.currencyconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.domain.FetchCurrencyRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(private val fetchCurrencyRateUseCase: FetchCurrencyRateUseCase) :
    ViewModel() {
    // TODO: Implement the ViewModel

    private val _currencyList = MutableLiveData<ResultData<List<CurrencyRateUI>>>()
    val currencyList: LiveData<ResultData<List<CurrencyRateUI>>>
        get() = _currencyList


    init {
        //TODO: Make checks to see if Data is required to be loaded first time
        viewModelScope.launch {
            try {
                fetchCurrencyRateUseCase.refreshCurrencyRateFromAPI().collect {
                    _currencyList.value = it
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _currencyList.value =
                    ResultData.Exception(ex, "An Unknown Error Occurred - ${ex.message}")
            }
        }
    }

    fun loadList() {

    }

}
