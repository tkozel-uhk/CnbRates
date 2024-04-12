package cz.uhk.cnbrates.tools

import android.content.Context
import com.google.gson.Gson

/**
 * Ze souboru assets nacte mapu znaku odpovidajicich vlajek <kod, vlajka>
 */
fun loadFlags(context: Context) : Map<String,String> {
    val input = context.assets.open("flags.json")
    val gson = Gson()
    val mapa = gson.fromJson(input.reader().readText(), Map::class.java) as Map<String, String>
    return mapa
}