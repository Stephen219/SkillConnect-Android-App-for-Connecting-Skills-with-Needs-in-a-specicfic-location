package com.example.mob_dev_portfolio.Data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mob_dev_portfolio.R

class RecentSearchAdapter(
    private val recentSearchList: List<String>,
    private val onItemClick: (String) -> Unit // Click handler
) : RecyclerView.Adapter<RecentSearchAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchText: TextView = itemView.findViewById(R.id.search_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_serch_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val search = recentSearchList[position]
        holder.searchText.text = search

        holder.itemView.setOnClickListener {
            onItemClick(search)
        }
    }

    override fun getItemCount(): Int {
        return recentSearchList.size
    }
}
