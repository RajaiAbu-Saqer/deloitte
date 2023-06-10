package com.rajai.deloitte.generic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rajai.deloitte.ui.home.dashboard.Results
import com.rajai.deloitte.utility.DrawableTools

@BindingAdapter("load_image")
fun ImageView.loadImage(resource: Int) {
    setImageResource(resource)
}

@BindingAdapter("load_image")
fun ImageView.loadImage(url: String?) {
    url?.let {
        DrawableTools.setImageUrl(this, it)
    }
}

@BindingAdapter("load_dash_board_image")
fun ImageView.loadDashBoardImage(results: Results?) {
    results?.let { result ->

        DrawableTools.setImageUrl(
            this,
            result.media?.firstOrNull()?.mediaMetadata?.firstOrNull()?.url ?: ""
        )
    }
}

abstract class GenericRecyclerAdapter<VIEW_HOLDER_TYPE>(
    var recyclerView: RecyclerView,
    var layoutId: Int
) : RecyclerView.Adapter<GenericViewHolder>() {
    var itemsList = ArrayList<VIEW_HOLDER_TYPE>()
    protected var orientation = RecyclerView.VERTICAL
    private var loading = true
    private var pastItemsVisible = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private val layoutManager by lazy {
        LinearLayoutManager(recyclerView.context, orientation, false)
    }

    init {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = this
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GenericViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val viewDataBinding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, viewGroup, false)
        return GenericViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(viewHolder: GenericViewHolder, position: Int) {
        bindViewHolder(
            viewHolder.itemView,
            position,
            itemsList[position],
            viewHolder.viewDataBinding
        )
    }

    public var currentPosition: Int = 0
        get() = field                     // getter
        set(value) {
            field = value
        }

    override fun getItemCount() = itemsList.size
    override fun getItemViewType(position: Int) = position
    fun getItem(position: Int) = itemsList[position]
    override fun getItemId(position: Int) = 0.toLong()

    fun add(item: VIEW_HOLDER_TYPE): GenericRecyclerAdapter<VIEW_HOLDER_TYPE> {
        itemsList.add(item)
        return this
    }

    fun add(position: Int, item: VIEW_HOLDER_TYPE): GenericRecyclerAdapter<VIEW_HOLDER_TYPE> {
        itemsList.add(position, item)
        return this
    }

    open fun add(list: List<VIEW_HOLDER_TYPE>?): GenericRecyclerAdapter<VIEW_HOLDER_TYPE> {
        itemsList.addAll(list!!)
        return this
    }

    fun refresh(): GenericRecyclerAdapter<*> {
        recyclerView.post { notifyDataSetChanged() }
        return this
    }

    fun removeItem(item: VIEW_HOLDER_TYPE): GenericRecyclerAdapter<VIEW_HOLDER_TYPE> {
        itemsList.remove(item)
        return this
    }

    fun clear(): GenericRecyclerAdapter<VIEW_HOLDER_TYPE> {
        itemsList.clear()
        return this
    }

    fun addOnFinishScrollListener(onFinishScroll: () -> Unit): GenericRecyclerAdapter<VIEW_HOLDER_TYPE> {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastItemsVisible = layoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastItemsVisible >= totalItemCount) {
                            loading = false
                            onFinishScroll()
                            loading = true
                        }
                    }
                }
            }
        })
        return this
    }

    protected abstract fun bindViewHolder(
        itemView: View,
        position: Int,
        item: VIEW_HOLDER_TYPE,
        viewDataBinding: ViewDataBinding
    )

    fun reverse(): GenericRecyclerAdapter<VIEW_HOLDER_TYPE> {
        itemsList.reverse()
        return this
    }
}

abstract class FilterableGenericRecyclerAdapter<MODEL>(recyclerView: RecyclerView, layoutId: Int) :
    GenericRecyclerAdapter<MODEL>(recyclerView, layoutId), Filterable {
    var arrayListFiltered = ArrayList<MODEL>()

    init {
        arrayListFiltered = itemsList
    }

    override fun onBindViewHolder(viewHolder: GenericViewHolder, position: Int) {
        bindViewHolder(
            viewHolder.itemView,
            position,
            arrayListFiltered[position],
            viewHolder.viewDataBinding
        )
    }

    override fun getItemCount() = arrayListFiltered.size

}