package com.mf.multipleviewtypes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView

class ColorAdapter(private val colors: List<Int>) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val appCompatImageView: AppCompatImageView = itemView.findViewById(R.id.appCompatImageView)

        fun bind(color: Int) {
            appCompatImageView.setBackgroundColor(color)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColorAdapter.ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false))
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors[position])
    }

    override fun getItemCount(): Int {
        return colors.count()
    }

}