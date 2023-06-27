package com.exam.assignment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exam.assignment.model.RelatedTopics
import com.exam.assignment.databinding.ListItemBinding

class TopicsAdapter(var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<TopicsAdapter.CustomViewHolder>() {
    private var relatedTopicsList: List<RelatedTopics> = listOf()
    private var relatedTopicFilterList: List<RelatedTopics> = listOf()

    interface OnItemClickListener {
        fun onItemClick(topic: RelatedTopics)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ListItemBinding.inflate(layoutInflater, parent, false)
        return CustomViewHolder(itemBinding)
    }

    fun setItems(list: List<RelatedTopics>) {
        this.relatedTopicsList = list
        this.relatedTopicFilterList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return relatedTopicFilterList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindTo(relatedTopicFilterList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(relatedTopicFilterList[position])
        }
    }

    class CustomViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(data: RelatedTopics) {
            binding.data = data
        }
    }

    fun filter(query: String) {
        relatedTopicFilterList = if (query.isEmpty()) relatedTopicsList else {
            val filteredList = ArrayList<RelatedTopics>()
            relatedTopicsList
                .filter {
                    it.getName().lowercase().contains(query.lowercase())
                }
                .forEach { filteredList.add(it) }
            filteredList
        }
        notifyDataSetChanged()
    }
}