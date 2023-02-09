package com.aj.currencycalculator.ui.currencydetail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.domain.conversionhistory.SearchHistoryUseCase
import com.aj.currencycalculator.domain.popularcurrencies.PopularCurrenciesUseCase
import com.aj.currencycalculator.ui.model.CurrencyUI
import com.aj.currencycalculator.ui.model.PopularCurrenciesConversionUI
import com.aj.currencycalculator.ui.model.SearchHistoryUI
import com.aj.currencycalculator.util.AppConstant
import com.aj.currencycalculator.util.DateTimeUtil
import com.aj.currencycalculator.util.extension.removeDotConvertToDouble
import com.aj.currencycalculator.util.extension.translateToError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class CurrencyConversionDetailViewModel @Inject constructor(
    private val searchHistoryUseCase: SearchHistoryUseCase,
    private val popularCurrenciesUseCase: PopularCurrenciesUseCase
) :
    ViewModel() {

    private val _searchHistoryList = MutableLiveData<ResultData<List<SearchHistoryUI>>>()
    val searchHistoryList: LiveData<ResultData<List<SearchHistoryUI>>>
        get() = _searchHistoryList


    private val _popularCurrencies =
        MutableLiveData<ResultData<PopularCurrenciesConversionUI>>()
    val popularCurrencies: LiveData<ResultData<PopularCurrenciesConversionUI>>
        get() = _popularCurrencies

    init {
        loadSearchHistoryData()
    }

    private fun loadSearchHistoryData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =
                    searchHistoryUseCase.getHistoryForDays(AppConstant.SEARCH_HISTORY_DAYS)
                when (result) {
                    is ResultData.Success -> {
                        result.data?.let {
                            _searchHistoryList.postValue(
                                ResultData.Success(
                                    prepareListDataThroughDate(
                                        it,
                                        AppConstant.SEARCH_HISTORY_DAYS
                                    )
                                )
                            )
                        }
                    }

                    is ResultData.Failed -> {
                        _searchHistoryList.postValue(result)
                    }

                    is ResultData.Exception -> {
                        _searchHistoryList.postValue(result)
                    }

                    else -> {

                    }
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
                _searchHistoryList.postValue(ex.translateToError())
            }
        }
    }


    /**
     * Prepare list of search queries w.r.t day Header
     */
    @SuppressLint("SimpleDateFormat")
    private fun prepareListDataThroughDate(
        list: List<SearchHistoryUI.SearchHistory>,
        pastDays: Int
    ): List<SearchHistoryUI> {
        val result = ArrayList<SearchHistoryUI>()
        var prevDays = pastDays
        var listOfData = list
        while (prevDays >= 0 && listOfData.isNotEmpty()) {
            val dateOfDay = DateTimeUtil.getMillisOfLastXDays(prevDays)
            val sdf = SimpleDateFormat("yyyyMMdd")
            if (dateOfDay != null) {
                val (desired, remaining) = listOfData.partition { searchHistory ->
                    sdf.format(searchHistory.dateTime).equals(sdf.format(dateOfDay))
                }
                listOfData = remaining
                //Prepare result list of header - Data
                if (desired.isNotEmpty()) {
                    val header = SearchHistoryUI.DateHeader(DateTimeUtil.getDate(dateOfDay))
                    result.add(header)
                    result.addAll(desired)
                }
            }
            prevDays--
        }
        return result
    }

    fun loadPopularCurrencies(
        baseCurrencyCode: String,
        baseCurrencyUserInput: String,
        targetList: ArrayList<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = popularCurrenciesUseCase.getPopularCurrencies(
                baseCurrencyCode,
                baseCurrencyUserInput,
                targetList
            )

            when (result) {
                is ResultData.Success -> {
                    result.data?.let {
                        val popularCurrenciesConversions = PopularCurrenciesConversionUI(
                            CurrencyUI(
                                baseCurrencyCode,
                                baseCurrencyUserInput.removeDotConvertToDouble() ?: 0.0
                            ), it
                        )
                        _popularCurrencies.postValue(ResultData.Success(popularCurrenciesConversions))
                    } ?: run {
                        _popularCurrencies.postValue(
                            ResultData.Failed(
                                "No Data",
                                "No Popular currencies found - Try refreshing data from server"
                            )
                        )
                    }
                }

                is ResultData.Failed -> {
                    _popularCurrencies.postValue(
                        ResultData.Failed(
                            "Conversion Failed",
                            "Data conversion Failed - Try again later"
                        )
                    )
                }

                is ResultData.Exception -> {
                    _popularCurrencies.postValue(result.exception?.translateToError())
                }

                else -> {

                }

            }


        }
    }
}