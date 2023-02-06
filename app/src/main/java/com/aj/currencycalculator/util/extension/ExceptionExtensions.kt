package com.aj.currencycalculator.util.extension

import com.aj.currencycalculator.data.model.ResultData
import retrofit2.HttpException
import java.io.IOException

fun Exception.translateToError(): ResultData.Exception {
    when (this) {
        is HttpException -> {
            return ResultData.Exception(
                this,
                "Oops, something went wrong! ${this.response()?.errorBody()?.string()}"
            )
        }

        is IOException -> {
            return ResultData.Exception(
                this,
                "Couldn't reach server, check your internet connection. Exception - ${this.message}"
            )
        }

        else -> {
            return ResultData.Exception(
                this,
                "Oops, something went wrong! ${this.message}"
            )
        }
    }
}
