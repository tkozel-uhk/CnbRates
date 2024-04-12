package cz.uhk.cnbrates.model

/**
 *Trida jedne meny (immutable)
 */
data class Currency(
    val country: String,
    val name: String,
    val quantity: Int,
    val code: String,
    val rate: Double
    )