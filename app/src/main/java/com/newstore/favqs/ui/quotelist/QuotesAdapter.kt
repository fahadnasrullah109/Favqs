package com.newstore.favqs.ui.quotelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newstore.favqs.base.BaseRecyclerViewAdapter
import com.newstore.favqs.data.models.response.Quote
import com.newstore.favqs.databinding.ItemQuoteLayoutBinding

class QuotesAdapter(val onItemClick: (item: Quote) -> Unit) :
    BaseRecyclerViewAdapter<QuotesAdapter.ViewHolder, Quote>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemQuoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val itemBinding: ItemQuoteLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Quote) {
            itemBinding.apply {
                authorValueTv.text = item.author
                tagValueTv.text = getTags(item.tags)
                quoteBodyTv.text = item.body
                favCountTv.text = item.favorites_count.toString()
                likeCountTv.text = item.upvotes_count.toString()
            }
            itemBinding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }

        private fun getTags(tags: List<String>): String {
            val tagBuilder = StringBuilder()
            for (i in tags.indices) {
                tagBuilder.append(tags[i])
                if (i != tags.lastIndex) {
                    tagBuilder.append(", ")
                }
            }
            return tagBuilder.toString()
        }
    }
}
