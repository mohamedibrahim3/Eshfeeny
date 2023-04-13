package com.example.eshfeenygraduationproject.eshfeeny.medicine

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.CategoryResponseItem
import com.example.eshfeenygraduationproject.databinding.MedicineDetailsItemBinding

class SideEffectsAdapter(): ListAdapter<CategoryResponseItem, SideEffectsAdapter.SideEffectsViewHolder>(SideEffectsDiffCallback()) {
    @SuppressLint("LongLogTag")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SideEffectsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MedicineDetailsItemBinding.inflate(inflater, parent, false)
        Log.i("CreateViewHolder SE sh8aal",binding.toString())
        return SideEffectsViewHolder(binding)
    }

    @SuppressLint("LongLogTag")
    override fun onBindViewHolder(holder: SideEffectsViewHolder, position: Int) {
        val item = getItem(position)
        Log.i("onBindViewHolderSE sh8aal",toString())
        holder.bind(item)
    }
    class SideEffectsViewHolder(private val binding: MedicineDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryResponseItem) {
            binding.txtIdDetails.text = "${category.sideEffects}"
            Log.i("ViewHolder SE sh8aal",toString())
        }
    }
}
class SideEffectsDiffCallback: DiffUtil.ItemCallback<CategoryResponseItem>() {
    override fun areItemsTheSame(
        oldItem: CategoryResponseItem,
        newItem: CategoryResponseItem
    ): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(
        oldItem: CategoryResponseItem,
        newItem: CategoryResponseItem
    ): Boolean {
        return oldItem == newItem
    }
}