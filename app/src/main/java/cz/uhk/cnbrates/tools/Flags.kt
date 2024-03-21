package cz.uhk.cnbrates.tools

import android.content.Context
import com.google.gson.Gson

data class Flag(
    val name: String,
    val flag: String
)

fun loadFlags(context: Context) : Map<String,String> {
    val input = context.assets.open("flags.json")
    val gson = Gson()
    val mapa = gson.fromJson(input.reader().readText(), Map::class.java) as Map<String, String>
    return mapa
}