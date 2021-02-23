package com.mf.multipleviewtypes

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(private val items: MutableList<Model>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val scrollStates = mutableMapOf<Int, Parcelable?>()

    private var mustShowProgressBar = true

    fun setMustShowProgressBar(mustShowProgressBar: Boolean) {
        this.mustShowProgressBar = mustShowProgressBar
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<Model>) {
        this.items.addAll(newItems)
        notifyItemInserted(this.items.count() - 1)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)

        if (holder is TypeCViewHolder) {
            val key = holder.layoutPosition
            scrollStates[key] =
                holder.rvColors.layoutManager?.onSaveInstanceState()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position > 0) {
            if (position == itemCount - 1) {
                return 4
            }

            return getItemsViewType(position)
        } else {
            return getItemsViewType(position)
        }

    }

    private fun getItemsViewType(position: Int): Int {
        return when (items[position]) {
            is TypeAModel -> 1
            is TypeBModel -> 2
            is TypeCModel -> 3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.type_a_item, parent, false)
                TypeAViewHolder(view)
            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.type_b_item, parent, false)
                TypeBViewHolder(view)
            }
            3 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.type_c_item, parent, false)
                TypeCViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
                TypeLoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TypeAViewHolder) {
            val item = items[position] as TypeAModel
            holder.imgNews.setImageDrawable(item.image)
            holder.txtNews.text = item.text
        } else if (holder is TypeBViewHolder) {
            val item = items[position] as TypeBModel
            holder.imgBanner.setImageDrawable(item.image)
        } else if (holder is TypeCViewHolder) {
            val typeCModel = items[position] as TypeCModel
            val colors = typeCModel.colors
            val colorAdapter = ColorAdapter(colors)
            holder.rvColors.adapter = colorAdapter

            val key = holder.layoutPosition
            val state = scrollStates[key]
            if (state != null) {
                holder.rvColors.layoutManager?.onRestoreInstanceState(state)
            } else {

                holder.rvColors.layoutManager?.scrollToPosition(0)
            }

        } else if (holder is TypeLoadingViewHolder) {
            holder.flLoading.visibility = if (mustShowProgressBar) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount(): Int {
        if (items.count() <= 0)
            return 0
        return items.count() + 1
    }

    inner class TypeAViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgNews = itemView.findViewById<ImageView>(R.id.imgNews)
        val txtNews = itemView.findViewById<TextView>(R.id.txtNews)
    }

    inner class TypeBViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgBanner = itemView.findViewById<ImageView>(R.id.imgBanner)
    }

    inner class TypeCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvColors = itemView.findViewById<RecyclerView>(R.id.rv_colors)
    }

    inner class TypeLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flLoading = itemView.findViewById<View>(R.id.fl_loading)
    }

}