package com.aj.currencycalculator.ui.currencyconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.currencycalculator.ui.model.CurrencyConverterUIState
import com.aj.currencycalculator.ui.model.CurrencyUI
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.domain.conversionhistory.SearchHistoryUseCase
import com.aj.currencycalculator.domain.currrencyconverter.CurrencyConverterUseCase
import com.aj.currencycalculator.domain.ratelist.GetSavedCurrencyRateListUseCase
import com.aj.currencycalculator.domain.updatedtime.GetCurrencyFetchTimeUseCase
import com.aj.currencycalculator.domain.updaterates.RefreshCurrencyRatesUseCase
import com.aj.currencycalculator.util.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val fetchCurrencyRateUseCase: RefreshCurrencyRatesUseCase,
    private val getCurrencyRateListUseCase: GetSavedCurrencyRateListUseCase,
    private val getCurrencyDateTimeFetch: GetCurrencyFetchTimeUseCase,
    private val currencyConverterUseCase: CurrencyConverterUseCase,
    private val searchHistoryUseCase: SearchHistoryUseCase,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    private val _uiState = MutableLiveData<ResultData<List<CurrencyUI>>>()
    val currencyList: LiveData<ResultData<List<CurrencyUI>>>
        get() = _uiState

    private val _lastFetchDateTime = MutableLiveData<String?>()
    val lastFetchDateTime: LiveData<String?> get() = _lastFetchDateTime

    private val _convertedCurrency = MutableLiveData<ResultData<Double>>()
    val convertedCurrency: LiveData<ResultData<Double>> get() = _convertedCurrency

    private val _userSelectionState by lazy {
        MutableLiveData<CurrencyConverterUIState>()
    }
    val userSelectionState: LiveData<CurrencyConverterUIState> get() = _userSelectionState

    init {
        if (sharedPrefHelper.isCurrencyRateSavedOnce()) {
            fetchLastSavedCurrencyRates()
        } else {
            refreshCurrencyRates()
        }
    }

    private fun fetchLastSavedCurrencyRates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getCurrencyRateListUseCase.getSavedCurrencyList().collect {
                    _uiState.postValue(ResultData.Success(it))
                    getLastFetchUpdateTime()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _uiState.postValue(
                    ResultData.Exception(
                        ex,
                        "An Error Occurred - ${ex.message}"
                    )
                )
            }
        }
    }

    fun refreshCurrencyRates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.postValue(ResultData.Loading())
                fetchCurrencyRateUseCase.refreshCurrencyRateFromAPI().collect {
                    _uiState.postValue(it)
                    sharedPrefHelper.setCurrencyRateSaved(true)
                    getLastFetchUpdateTime()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _uiState.postValue(
                    ResultData.Exception(
                        ex,
                        "An Error Occurred - ${ex.message}"
                    )
                )
            }
        }
    }

    private fun getLastFetchUpdateTime() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getCurrencyDateTimeFetch.getLastUpdateTime().collect {
                    _lastFetchDateTime.postValue(it)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _uiState.postValue(
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
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _convertedCurrency.postValue(ResultData.Loading())
                    currencyConverterUseCase.calculateCurrency(
                        inputCurrency,
                        baseCurrencyCode,
                        targetCurrencyCode
                    ).collect {
                        _convertedCurrency.postValue(it)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    //save State on Configuration change
    fun onStopEvent(
        inputCurrency: String?,
        baseCurrencyCode: String?,
        targetCurrencyCode: String?
    ) {
        _userSelectionState.value =
            CurrencyConverterUIState(
                baseCurrency = baseCurrencyCode,
                toCurrency = targetCurrencyCode,
                inputCurrency
            )
    }

}
