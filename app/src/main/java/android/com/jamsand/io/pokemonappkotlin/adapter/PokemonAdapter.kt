package android.com.jamsand.io.pokemonappkotlin.adapter

import android.com.jamsand.io.pokemonappkotlin.databinding.PokemonListItemBinding
import android.com.jamsand.io.pokemonappkotlin.model.Pokemon
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonHolder>(){

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
        Glide.with(holder.itemView.context).load(pokemon.url).
        into(holder.binding.imageViewThumbnail)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    inner class PokemonHolder(val binding: PokemonListItemBinding):
        RecyclerView.ViewHolder(binding.root){
    }



}