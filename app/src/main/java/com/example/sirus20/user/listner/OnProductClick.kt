package com.example.sirus20.user.listner

import com.example.sirus20.user.model.ProductModel

interface OnProductClick {
    fun onClick(productModel: ProductModel)
}