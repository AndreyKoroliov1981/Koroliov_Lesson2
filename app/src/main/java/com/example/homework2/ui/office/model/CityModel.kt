package com.example.homework2.ui.office.model

sealed class CityModel {
    data class CityRuModel(val id: Int, val name: String) : CityModel()
    data class CityByModel(val id: Int, val name: String) : CityModel()
}
