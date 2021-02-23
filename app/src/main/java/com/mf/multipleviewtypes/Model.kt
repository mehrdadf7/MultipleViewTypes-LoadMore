package com.mf.multipleviewtypes

import android.graphics.drawable.Drawable

sealed class Model

data class TypeAModel(val image: Drawable, val text: String) : Model()

data class TypeBModel(val image: Drawable) : Model()

data class TypeCModel(val colors: List<Int>) : Model()