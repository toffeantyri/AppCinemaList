package ru.testwork.appcinemalist.busines.model.jsonmodels

data class NYTimesReviewModel(
    val copyright: String,
    val has_more: Boolean,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)