package android.com.jamsand.io.pokemonappkotlin.model

data class Details( var name: String,
                    var url: String,
                    var height: Int,
                    var weight:String,
                    var types:Types) {

}
data class Types (var name:String){
    
}