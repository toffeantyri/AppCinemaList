package ru.testwork.appcinemalist.busines.model


data class FilterProduct(
    val id: String? = null,
    val productName: String? = null,
    var page: Int = 1,
    val pageSize: Int = 20,
    val discountProduct: Boolean? = null,
    val popularProducts: Boolean? = null,
    val similarProducts: Int? = null,
    val categoryFilter: Int? = null,
    var subcategory: Int? = null,
    //val sortBy: SortBy? = null,
) {
    fun toMap(): MutableMap<String, String?> {
        val queryMap: MutableMap<String, String?> = mutableMapOf(
            "page" to this.page.toString(),
            "page_size" to this.pageSize.toString()
        )
        this.categoryFilter?.let { queryMap["sarawan_category"] = it.toString() }
        this.discountProduct?.let { queryMap["discount_products"] = it.toString() }
        this.id?.let { queryMap["id"] = it }
        this.popularProducts?.let { queryMap["popular_products"] = it.toString() }
        this.productName?.let { queryMap["product_name"] = it }
        this.similarProducts?.let { queryMap["similar_product"] = it.toString() }
        this.subcategory?.let { queryMap["category"] = it.toString() }
//        this.sortBy?.let { sorting ->
//            when (sorting) {
//                SortBy.PRICE_ASC -> queryMap["ordering_price"] = "true"
//                SortBy.PRICE_DES -> queryMap["ordering_price"] = "false"
//                SortBy.ALPHABET -> queryMap["order"] = "name"
//                SortBy.DISCOUNT -> queryMap["order"] = "-discount"
//            }
//        }
        return queryMap
    }
}

