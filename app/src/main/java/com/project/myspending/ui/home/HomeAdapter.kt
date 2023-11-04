package com.project.myspending.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.myspending.R
import com.project.myspending.Spending
import com.project.myspending.databinding.ItemSpendingBinding

/**Adapter to display the main spending dashboard**/
class HomeAdapter(private val onItemClicked: (spending: Spending) -> Unit) :
    ListAdapter<Spending, RecyclerView.ViewHolder>(DiffCallback) {
    /**detects the change in data and update the specific position**/
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Spending>() {
            override fun areItemsTheSame(oldItem: Spending, newItem: Spending): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Spending, newItem: Spending): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SpendingViewHolder(
            ItemSpendingBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SpendingViewHolder -> holder.bind(getItem(position))
        }
    }

    /**ViewHolder for item**/
    inner class SpendingViewHolder(private val binding: ItemSpendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spending: Spending) {
            binding.purpose.text = spending.purpose
            binding.amount.text = binding.root.context.getString(
                R.string.rupee_symbol,
                spending.amount.toString()
            )
            binding.root.setOnClickListener {
                onItemClicked.invoke(spending)
            }
        }
    }
}
