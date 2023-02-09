package com.aj.currencycalculator.ui.currencydetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aj.currencycalculator.BR
import com.aj.currencycalculator.R
import com.aj.currencycalculator.databinding.RowSearchHistoryBinding
import com.aj.currencycalculator.databinding.RowSearchHistoryHeaderBinding
import com.aj.currencycalculator.ui.model.SearchHistoryUI

class SearchHistoryAdapter(private val list: List<SearchHistoryUI>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_SEARCH_HISTORY = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_search_history_header,
                parent,
                false
            )
        } else {
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_search_history,
                parent,
                false
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val data = list?.get(position)) {

            is SearchHistoryUI.DateHeader -> {
                (holder as ViewHolderHeader).bind(data)
            }

            is SearchHistoryUI.SearchHistory -> {
                (holder as ViewHolderSearchHistory).bind(data)
            }
            else -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return when (list?.get(position)) {
            is SearchHistoryUI.SearchHistory -> TYPE_HEADER
            else -> TYPE_SEARCH_HISTORY
        }
    }
}

private class ViewHolderHeader(itemRowBinding: RowSearchHistoryHeaderBinding) :
    RecyclerView.ViewHolder(itemRowBinding.root) {
    var itemRowBinding: RowSearchHistoryHeaderBinding

    init {
        this.itemRowBinding = itemRowBinding
    }

    fun bind(dataHeader: SearchHistoryUI.DateHeader) {
        itemRowBinding.setVariable(BR.header, dataHeader)
        itemRowBinding.executePendingBindings()
    }
}

private class ViewHolderSearchHistory(itemRowBinding: RowSearchHistoryBinding) :
    RecyclerView.ViewHolder(itemRowBinding.root) {
    var itemRowBinding: RowSearchHistoryBinding

    init {
        this.itemRowBinding = itemRowBinding
    }

    fun bind(obj: SearchHistoryUI.SearchHistory) {
        itemRowBinding.setVariable(BR.history, obj)
        itemRowBinding.executePendingBindings()
    }
}



