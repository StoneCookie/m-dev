package com.example.hw_6.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_6.R
import com.example.hw_6.database.Node
import com.example.hw_6.databinding.NodeListFragmentBinding
import com.example.hw_6.viewmodels.FilterState
import com.example.hw_6.viewmodels.NodeViewModel

class NodeListFragment : Fragment() {
    private lateinit var binding: NodeListFragmentBinding
    private val nodeModel: NodeViewModel by activityViewModels()
    private lateinit var pickNode: Node
    private lateinit var filterState: FilterState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = NodeListFragmentBinding.inflate(inflater, container, false)
        val pickNodeId = arguments?.getLong("id")
        if (pickNodeId != null) {
            nodeModel.getPickNode(pickNodeId)
                .observe(this, {
                    pickNode = it
                    setAdapter(it.id)
                })
        }
        nodeModel.nodes.observe(this, {
            if (pickNodeId != null) {
                nodeModel.sortNodes(pickNodeId)
            }
        })
        nodeModel.filterState.observe(this, {
            filterState = it
            if (pickNodeId != null) {
                nodeModel.sortNodes(pickNodeId)
            }
        })
        binding.childBtn.setOnClickListener {
            if (pickNodeId != null) {
                nodeModel.changeFilterState(FilterState.Children)
            }
        }
        binding.parentBtn.setOnClickListener {
            if (pickNodeId != null) {
                nodeModel.changeFilterState(FilterState.Parents)
            }
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter(id: Long) {
        val recyclerView = binding.nodeRecycler
        val adapter = NodeListAdapter(getColor = ::getBgColor)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.onItemClick = { pressedId ->
            if (filterState === FilterState.Children) {
                onAddChild(pressedId)
            } else {
                onAddParent(pressedId)
            }
        }
        nodeModel.sortNodes(id)
        nodeModel.filteredNodes.observe(this, { filteredNodes ->
            adapter.submitList(filteredNodes)
        })
    }

    private fun getBgColor(node: Node): Int {
        var color = ContextCompat.getColor(context!!, R.color.white)
        if (pickNode.nodes.contains(node)) {
            color = ContextCompat.getColor(context!!, R.color.relation)
        }

        if  (node.nodes.contains(pickNode)) {
            color = ContextCompat.getColor(context!!, R.color.teal_200)
        }

        return color
    }

    private fun onAddChild(childId: Long) {
        nodeModel.getPickNode(childId).observe(this, { childNode ->
            if (!pickNode.nodes.contains(childNode)) {
                pickNode.nodes.add(childNode)
                nodeModel.update(pickNode)
            }
        })
    }

    private fun onAddParent(parentId: Long) {
        nodeModel.getPickNode(parentId).observe(this, { parentNode ->
            if (!parentNode.nodes.contains(pickNode)) {
                parentNode.nodes.add(pickNode)
                nodeModel.update(parentNode)
            }
        })
    }
}