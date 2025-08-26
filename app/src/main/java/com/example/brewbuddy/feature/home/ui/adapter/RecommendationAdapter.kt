package com.example.brewbuddy.feature.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brewbuddy.core.data.local.entity.DrinkCacheEntity
import com.example.brewbuddy.core.model.Drink
import com.example.brewbuddy.databinding.ItemRecommendationBinding

class RecommendationAdapter(
    private val items: MutableList<Drink>
) : RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    inner class RecommendationViewHolder(
        private val binding: ItemRecommendationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
//            binding.tvRecommendationTitle.text = drink.name
            // Glide.with(binding.ivRecommendation.context)
            //     .load(drink.imageUrl)
            //     .into(binding.ivRecommendation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<Drink>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

