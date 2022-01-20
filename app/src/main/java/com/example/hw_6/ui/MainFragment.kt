package com.example.hw_6.ui

import AddFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_6.R
import com.example.hw_6.database.Node
import com.example.hw_6.databinding.FragmentMainBinding
import com.example.hw_6.viewmodels.NodeViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val model: NodeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setAdapter()
        setDialog()

        return binding.root
    }

    private fun setAdapter() {
        val recyclerView = binding.recyclerView
        val adapter = NodeListAdapter(getColor = ::getItemBG)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.onItemClick = { id ->
            val bundle = bundleOf("id" to id)
            Navigation.findNavController(binding.root).navigate(R.id.navigateToItem, bundle)
        }
        model.nodes.observe(this, { nodes ->
            adapter.submitList(nodes)
        })
    }

    private fun getItemBG(node: Node): Int {
        var itemColor = ContextCompat.getColor(context!!, R.color.white)
        val itemParent = node.nodes.size > 0
        val itemChild = model.nodes.value?.any { it.nodes.contains(node) }
        when {
            itemParent and (itemChild == true) -> {
                itemColor = ContextCompat.getColor(context!!, R.color.child_and_parent)
            }
            itemParent -> {
                itemColor = ContextCompat.getColor(context!!, R.color.parent)
            }
            itemChild == true -> {
                itemColor = ContextCompat.getColor(context!!, R.color.child)
            }
        }

        return itemColor
    }

    private fun setDialog() {
        val dialog = AddFragment()
        val supportFragmentManager = activity?.supportFragmentManager
        binding.fab.setOnClickListener {
            if (supportFragmentManager != null) {
                dialog.show(supportFragmentManager, "AddFragment")
            }
        }
    }
}