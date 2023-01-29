package com.development.calculate

sealed class CalculatorOperations(val symbols: String) {
    object Add : CalculatorOperations("+")
    object Subtract : CalculatorOperations("-")
    object Multiply : CalculatorOperations("x")
    object Divide : CalculatorOperations("/")


}
