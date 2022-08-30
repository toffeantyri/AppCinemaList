package ru.testwork.appcinemalist.repository


import com.squareup.moshi.Json


data class Product(
    @field: Json(name = "id") var id: Int? = null,
    @field: Json(name = "product") var product: Product? = null,
    @field: Json(name = "name") var name: String? = null,
    @field: Json(name = "price_type") val priceType: String? = null,
    @field: Json(name = "unit_quantity") val unitQuantity: Double? = null,
    @field: Json(name = "description") val description: String? = null,
    @field: Json(name = "quantity") var quantity: Int? = null,
    @field: Json(name = "price") val price: Double? = null,
    @field: Json(name = "discount") val discount: Double? = null
)

data class ProductsItemResponse(
    @field:Json(name = "product") val productId: Int? = null,
    @field:Json(name = "quantity") var quantity: Int = 0,
)

