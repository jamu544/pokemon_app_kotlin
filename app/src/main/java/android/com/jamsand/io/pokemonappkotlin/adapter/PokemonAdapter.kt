package android.com.jamsand.io.pokemonappkotlin.adapter

import android.com.jamsand.io.pokemonappkotlin.databinding.PokemonListItemBinding
import android.com.jamsand.io.pokemonappkotlin.model.Pokemon
import android.com.jamsand.io.pokemonappkotlin.view.OnClickListener
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class PokemonAdapter (private val onClickListener: OnClickListener):
    RecyclerView.Adapter<PokemonAdapter.PokemonHolder>(){

    var pokemons = mutableListOf<Pokemon.PokemonArray>()
    fun setPokemonList(pokemonItems: List<Pokemon.PokemonArray>){
        this.pokemons = pokemonItems.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PokemonListItemBinding.inflate(inflater, parent, false)
        return PokemonHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int){
        val pokemon = pokemons[position]
        holder.binding.pokemonName.text = pokemon.name
        pokemon.pokemonID = position
        holder.itemView.setOnClickListener { onClickListener.onClick(pokemon,pokemon.pokemonID) }

//        Glide.with(holder.itemView.context).load(pokemon.url).
//        into(holder.binding.imageViewThumbnail)
//        Picasso.with(holder.binding.root.context)
//            .load("https://pokeapi.co/api/v2/pokemon/1/")
//            .into(holder.binding.imageViewThumbnail)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    inner class PokemonHolder(val binding: PokemonListItemBinding):
        RecyclerView.ViewHolder(binding.root){
    }



}