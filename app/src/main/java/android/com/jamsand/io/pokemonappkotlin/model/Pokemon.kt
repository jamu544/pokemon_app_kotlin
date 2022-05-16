package android.com.jamsand.io.pokemonappkotlin.model

data class Pokemon(var count: String,
                   var next: String,
                   val results:List<PokemonArray>) {

data class PokemonArray(var name:String,var url:String)
}