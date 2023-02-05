package com.aj.currencycalculator.data.network


import com.aj.currencycalculator.data.network.model.CurrencyResponse
import com.aj.currencycalculator.util.AppConstant
import com.aj.currencycalculator.util.NetworkConstant
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {
    @GET(NetworkConstant.GET_LATEST)
    suspend fun getCurrencies(
        @Query("base") source: String = AppConstant.BASE_CURRENCY
    ): CurrencyResponse

}
