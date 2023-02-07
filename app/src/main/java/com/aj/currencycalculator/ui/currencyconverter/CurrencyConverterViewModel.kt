package com.aj.currencycalculator.ui.currencyconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.currencycalculator.data.model.CurrencyRateUI
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.domain.currrencyconverter.CurrencyConverterUseCase
import com.aj.currencycalculator.domain.ratelist.GetSavedCurrencyRateListUseCase
import com.aj.currencycalculator.domain.updatedtime.GetCurrencyFetchTimeUseCase
import com.aj.currencycalculator.domain.updaterates.RefreshCurrencyRatesUseCase
import com.aj.currencycalculator.util.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val fetchCurrencyRateUseCase: RefreshCurrencyRatesUseCase,
    private val getCurrencyRateListUseCase: GetSavedCurrencyRateListUseCase,
    private val getCurrencyDateTimeFetch: GetCurrencyFetchTimeUseCase,
    private val currencyConverterUseCase: CurrencyConverterUseCase,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    private val _currencyList = MutableLiveData<ResultData<List<CurrencyRateUI>>>()
    val currencyList: LiveData<ResultData<List<CurrencyRateUI>>>
        get() = _currencyList

    private val _lastFetchDateTime = MutableLiveData<String?>()
    val lastFetchDateTime: LiveData<String?> get() = _lastFetchDateTime


    private val _convertedCurrency = MutableLiveData<ResultData<Double>>()
    val convertedCurrency: LiveData<ResultData<Double>> get() = _convertedCurrency

    init {
        if (sharedPrefHelper.isCurrencyRateSavedOnce()) {
            fetchLastSavedCurrencyRates()
        } else {
            refreshCurrencyRates()
        }
    }

    private fun fetchLastSavedCurrencyRates() {
        viewModelScope.launch {
            try {
                getCurrencyRateListUseCase.getSavedCurrencyList().collect {
                    _currencyList.value = ResultData.Success(it)
                    getLastFetchUpdateTime()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _currencyList.value =
                    ResultData.Exception(ex, "An Error Occurred - ${ex.message}")
            }
        }
    }

    fun refreshCurrencyRates() {
        viewModelScope.launch {
            try {
                fetchCurrencyRateUseCase.refreshCurrencyRateFromAPI().collect {
                    _currencyList.value = it
                    sharedPrefHelper.setCurrencyRateSaved(true)
                    getLastFetchUpdateTime()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _currencyList.value =
                    ResultData.Exception(ex, "An Unknown Error Occurred - ${ex.message}")
            }
        }
    }

    private fun getLastFetchUpdateTime() {
        viewModelScope.launch {
            try {
                getCurrencyDateTimeFetch.getLastUpdateTime().collect {
                    _lastFetchDateTime.postValue(it)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _currencyList.postValue(
                    ResultData.Exception(
                        ex,
                        "An Error Occurred - ${ex.message}"
                    )
                )
            }
        }
    }

    fun onCurrencyInputChanged(
        inputCurrency: String?,
        baseCurrencyCode: String?,
        targetCurrencyCode: String?
    ) {
        if (!inputCurrency.isNullOrEmpty() && !baseCurrencyCode.isNullOrEmpty() && !targetCurrencyCode.isNullOrEmpty()) {
            viewModelScope.launch {
                try {
                    currencyConverterUseCase.calculateCurrency(
                        inputCurrency,
                        baseCurrencyCode,
                        targetCurrencyCode
                    ).collect {
                        _convertedCurrency.value = it
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }
}
