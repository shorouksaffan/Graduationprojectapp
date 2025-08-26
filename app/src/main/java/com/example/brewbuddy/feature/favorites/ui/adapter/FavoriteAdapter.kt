package com.example.brewbuddy.feature.favorites.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brewbuddy.core.model.Drink
import com.example.brewbuddy.databinding.ItemRecommendationBinding

class FavoritesAdapter(
    private val items: MutableList<Drink>,
    private val onRemoveClick: (Drink) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    inner class FavoritesViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
//            binding.tvRecommendationTitle.text = drink.name
//            Glide.with(binding.ivRecommendation.context)
//                .load(drink.imageUrl)
//                .into(binding.ivRecommendation)
//
//            binding.root.setOnClickListener {
//                // Optional: open details
//            }
//
//            binding.ivRecommendation.setOnLongClickListener {
//                onRemoveClick(drink)
//                true
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = ItemRecommendationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<Drink>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
