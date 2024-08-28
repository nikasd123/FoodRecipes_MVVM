package com.kaz4.domain.models

data class Category(
    val id: String,
    val category: String,
    var isActive: Boolean
)