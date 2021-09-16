package model

data class GeoObject(
    val country: Country,
    val district: Any,
    val locality: Locality,
    val province: Province
)
