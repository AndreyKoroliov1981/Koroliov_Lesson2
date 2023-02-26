package com.example.homework2.ui.main.model

import com.example.homework2.ui.main.recycler.DisplayableItem

data class Header(
    val pictureId: Int,
    val headerUp: String,
    val headerDown: String,
) : DisplayableItem