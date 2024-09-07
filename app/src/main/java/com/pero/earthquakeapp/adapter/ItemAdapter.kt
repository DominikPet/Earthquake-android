package com.pero.earthquakeapp.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pero.earthquakeapp.EARTHQUAKE_PROVIDER_CONTENT_URI
import com.pero.earthquakeapp.ItemPagerActivity
import com.pero.earthquakeapp.POSITION
import com.pero.earthquakeapp.R
import com.pero.earthquakeapp.framework.startActivity
import com.pero.earthquakeapp.model.Item

class ItemAdapter(private val context: Context,
                  private val items: MutableList<Item>)
    : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        fun bind(item: Item) {
            tvTitle.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnLongClickListener {
            val item = items[position]
            context.contentResolver.delete(
                ContentUris.withAppendedId(EARTHQUAKE_PROVIDER_CONTENT_URI, item._id!!),
                null, null
            )
            items.removeAt(position)
            notifyDataSetChanged()

            true
        }

        holder.itemView.setOnClickListener {
            context.startActivity<ItemPagerActivity>(POSITION, position)
        }

        holder.bind(items[position])
    }
}