package com.mercadolivro.enums

enum class Errors(val message:String, val code: String) {

    ML000("Access Denied", "ML-000"),
    ML001("Invalid Request", "ML-001"),

    ML101("Book [%s], not exists.", "ML-101"),
    ML102("Cannot update book with status [%s]","ML-102"),

    ML201("Customer [%s], not exists.", "ML-201")

}