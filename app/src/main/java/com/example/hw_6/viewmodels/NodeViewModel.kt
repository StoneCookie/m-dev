package com.example.hw_6.viewmodels

import androidx.lifecycle.*
import com.example.hw_6.database.Node
import com.example.hw_6.repository.NodeRepository
import kotlinx.coroutines.*

enum class FilterState {
    Children, Parents
}

class NodeViewModel(private val repository: NodeRepository) : ViewModel() {
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    val nodes = repository.nodes.asLiveData()
    var filterState: MutableLiveData<FilterState> = MutableLiveData(FilterState.Children)
    val filteredNodes: MutableLiveData<List<Node>> = MutableLiveData(emptyList())

    fun insert(node: Node) {
        viewModelScope.launch(defaultDispatcher) {
            repository.insert(node)
        }
    }

    fun update(node: Node) {
        viewModelScope.launch(defaultDispatcher) {
            repository.update(node)
        }
    }

    fun changeFilterState(newState: FilterState) {
        viewModelScope.launch(defaultDispatcher) {
            filterState.postValue(newState)
        }
    }

    fun getPickNode(id: Long): LiveData<Node> {
        val pickNode = MutableLiveData<Node>()
        viewModelScope.launch(defaultDispatcher) {
            val node = repository.getNode(id)
            pickNode.postValue(node!!)
        }

        return pickNode
    }

    fun sortNodes(id: Long) {
        viewModelScope.launch(defaultDispatcher) {
            if (filterState.value === FilterState.Children) {
                filterByChildren(id)
            } else if (filterState.value === FilterState.Parents) {
                filterByParents(id)
            }
        }
    }

    private fun filterByChildren(id: Long) {
        val pickNode = nodes.value?.find { it.id == id }
        val filteredNodes = nodes.value?.filter { !it.nodes.contains(pickNode) && id != it.id }
        this.filteredNodes.postValue(filteredNodes!!)
    }

    private fun filterByParents(id: Long) {
        val pickNode = nodes.value?.find { it.id == id }
        if (pickNode != null) {
            val filteredNodes =
                nodes.value?.filter { !pickNode.nodes.contains(it) && id != it.id }
            this.filteredNodes.postValue(filteredNodes!!)
        }
    }
}

class NodeFactory(private val repository: NodeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NodeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            
            return NodeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unidentified ViewModel")
    }
}