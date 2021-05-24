package com.kdroid.coroutinewithretrofitdemo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kdroid.coroutinewithretrofitdemo.R
import com.kdroid.coroutinewithretrofitdemo.databinding.ItemCountryBinding
import com.kdroid.coroutinewithretrofitdemo.model.Country

class CountryListAdapter(private var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        with(holder) {
            binding.name.text = countries[position].countryName
            binding.capital.text = countries[position].capital
            binding.imageView.loadImage(countries[position].flag)
        }
    }
    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCountryBinding.bind(view);

    }

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    // extension function
    private fun ImageView.loadImage(uri: String?) {
        val options = RequestOptions()
            .error(R.mipmap.ic_launcher_round)
        Glide.with(this.context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(this)
    }

}