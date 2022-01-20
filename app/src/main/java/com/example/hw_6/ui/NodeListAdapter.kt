package com.example.hw_6.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_6.database.Node
import com.example.hw_6.databinding.ListItemBinding


class NodeListAdapter(
    private val getColor: (node: Node) -> Int,
) : ListAdapter<Node, NodeListAdapter.NodeViewHolder>(NODE_COMPARATOR) {
    var onItemClick: ((id: Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return NodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    inner class NodeViewHolder internal constructor(
        private val binding: ListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(node: Node) = binding.run {
            textView.text = node.value
            listItem.setOnClickListener {
                onItemClick?.invoke(node.id)
            }
            val bgColor = getColor(node)
            textView.setBackgroundColor(bgColor)
        }
    }

    companion object {
        private val NODE_COMPARATOR = object : DiffUtil.ItemCallback<Node>() {
            override fun areItemsTheSame(oldItem: Node, newItem: Node): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Node, newItem: Node): Boolean {
                return oldItem.value == newItem.value
            }
        }
    }
}
