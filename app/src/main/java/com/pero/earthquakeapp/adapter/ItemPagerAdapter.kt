package com.pero.earthquakeapp.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pero.earthquakeapp.EARTHQUAKE_PROVIDER_CONTENT_URI
import com.pero.earthquakeapp.R
import com.pero.earthquakeapp.model.Item

class ItemPagerAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvMagnitude = itemView.findViewById<TextView>(R.id.tvMagnitude)
        private val tvPlace = itemView.findViewById<TextView>(R.id.tvPlace)
        fun bind(item: Item) {
            tvTitle.text = item.title
            tvMagnitude.text = item.mag.toString() + " mag"
            tvPlace.text = item.place
            ivRead.setImageResource(if(item.read) R.drawable.approved else R.drawable.red_flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_page, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.ivRead.setOnClickListener {
            updateItem(position)
        }


        holder.bind(items[position])
    }

    private fun updateItem(position: Int) {
        val item = items[position]
        item.read = !item.read
        context.contentResolver.update(
            ContentUris.withAppendedId(EARTHQUAKE_PROVIDER_CONTENT_URI, item._id!!),
            ContentValues().apply {
                put(Item::read.name, item.read)
            },
            null,
            null
        )
        notifyItemChanged(position)
    }
}