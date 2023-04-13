package com.example.domain.entity

class CategoryResponse(
    id: Any,
    description: String,
    useCase: Any,
    nameAr: String,
    nameEn: String,
    amount: String,
    images: Any,
    price: Any,
    usage: Any,
    volume: String,
    warning: Any,
    sideEffects: Any
) : ArrayList<CategoryResponseItem>()