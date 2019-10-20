package com.example.findit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findit.ItemsFragmentDirections
import com.example.findit.databinding.ListItemLostBinding
import com.example.findit.models.Item
import com.example.findit.viewmodels.ItemViewModel

class ItemAdapter: ListAdapter<Item, ItemAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemLostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { item ->
            with(holder) {
                itemView.tag = item
                bind(createOnClickListener(item), item)
            }
        }
    }

    private fun createOnClickListener(item: Item): View.OnClickListener {
        return View.OnClickListener {
            val direction = ItemsFragmentDirections.actionItemsFragmentToItemDetailsFragment(item)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(private val binding: ListItemLostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: Item) {
            with(binding) {
                clickListener = listener
                viewModel = ItemViewModel(item)
                executePendingBindings()
            }
        }
    }
}

private class ItemDiffCallback: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}