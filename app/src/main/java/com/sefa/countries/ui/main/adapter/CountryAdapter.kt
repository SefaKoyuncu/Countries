package com.sefa.countries.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sefa.countries.data.model.Countries
import com.sefa.countries.databinding.CardCountryBinding
import com.sefa.countries.ui.main.MainFragmentDirections
import com.sefa.countries.utils.transition

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CardViewHolder>()
{
 inner class CardViewHolder(val binding: CardCountryBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Countries>(){
        override fun areContentsTheSame(oldItem: Countries, newItem: Countries): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Countries, newItem: Countries): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list : List<Countries>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        return CardViewHolder(
            CardCountryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        val countryResult = differ.currentList[position]

        holder.binding.apply {
            textViewCountryName.text = countryResult.name
            textViewCountryCapitalCity.text = countryResult.capital
            imageViewFlag.load(countryResult.image)

            cardViewCountry.setOnClickListener{view->
                val action : NavDirections = MainFragmentDirections.fromMaintoDetails(countryResult)
                Navigation.transition(view, action)
            }
        }

    }
}