package com.rajai.deloitte.ui.home.dashboard

import android.view.View
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.rajai.deloitte.R
import com.rajai.deloitte.databinding.DashboardCellBinding
import com.rajai.deloitte.generic.GenericRecyclerAdapter

class DashboardNewsAdapter(
    recycler: RecyclerView,
    private val itemClickedAction: (Results) -> Unit,
    val onFilterEnd: (arrayListFiltered: ArrayList<Results>) -> Unit
) : GenericRecyclerAdapter<Results>(recycler, R.layout.dashboard_cell), Filterable {
    var arrayListFiltered = ArrayList<Results>()

    init {
        arrayListFiltered = itemsList
    }

    override fun bindViewHolder(
        itemView: View,
        position: Int,
        item: Results,
        viewDataBinding: ViewDataBinding
    ) {
        (viewDataBinding as DashboardCellBinding).apply {
            dashBoardResult = arrayListFiltered[position]
            itemView.setOnClickListener {
                itemClickedAction.invoke(arrayListFiltered[position])
            }
        }
    }

    override fun getItemCount() = arrayListFiltered.size
    override fun getFilter(): Filter {
        return object : Filter() {
            public override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString: String = charSequence?.toString() ?: ""
                when {
                    charString.isEmpty() -> {
                        arrayListFiltered = itemsList
                    }
                    else -> {
                        val filteredList = ArrayList<Results>()
                        for (i in itemsList.indices)
                            if (itemsList[i].title?.contains(charString, ignoreCase = true) == true
                            ) filteredList.add(itemsList[i])
                        arrayListFiltered = filteredList
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = arrayListFiltered
                filterResults.count = arrayListFiltered.size
                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults?,
            ) {
                results?.values?.let {
                    arrayListFiltered = it as ArrayList<Results>
                }
                refresh()
                onFilterEnd(arrayListFiltered)
            }
        }
    }
}