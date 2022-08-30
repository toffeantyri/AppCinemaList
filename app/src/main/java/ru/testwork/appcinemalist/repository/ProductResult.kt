package ru.testwork.appcinemalist.repository

import com.squareup.moshi.Json

data class ProductResult(
    @field: Json(name = "results") val results: List<Product>
)