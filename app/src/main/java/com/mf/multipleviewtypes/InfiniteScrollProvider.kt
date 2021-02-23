package com.mf.multipleviewtypes

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollProvider {

    private var onLoadMoreListener: OnLoadMoreListener? = null

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView

    var isLoading = false
    var lastVisibleItem = 0
    var previousItemCount = 0
    var totalItemCount = 0

    fun attach(recyclerView: RecyclerView, onLoadMoreListener: OnLoadMoreListener?) {
        this.recyclerView = recyclerView
        this.onLoadMoreListener = onLoadMoreListener
        gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
        setInfiniteScrollGrid()
    }

    private fun setInfiniteScrollGrid() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                totalItemCount = gridLayoutManager.itemCount
                if (previousItemCount > totalItemCount) {
                    previousItemCount = totalItemCount - 3
                }
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
                if (totalItemCount > 3) {
                    if (previousItemCount <= totalItemCount && isLoading) {
                        isLoading = false
                    }
                    if (!isLoading && lastVisibleItem > totalItemCount - 3 && totalItemCount > previousItemCount) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener?.onLoadMore()
                        }
                        isLoading = true
                        previousItemCount = totalItemCount
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

}