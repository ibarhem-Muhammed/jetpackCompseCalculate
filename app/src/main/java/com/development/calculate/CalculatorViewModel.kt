package com.development.calculate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel :ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set


    fun onAction(actions: CalculatorActions) {
        when (actions) {
            is CalculatorActions.Calculate -> performCalculation()
            is CalculatorActions.Clear -> state = CalculatorState()
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Delete -> performDeletion()
            is CalculatorActions.Number -> enterNumber(actions.number)
            is CalculatorActions.Operation -> enterOperation(actions.operations)
        }

    }

    private fun enterOperation(operations: CalculatorOperations) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operations = operations)
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operations == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }
        if (state.number2.length >= MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )
    }

    private fun performDeletion() {

        when {
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operations != null -> state = state.copy(
                operations = null
            )
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }

    }

    private fun enterDecimal() {
        if(state.operations == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        } else if(!state.number2.contains(".") && state.number2.isNotBlank()) {
            state = state.copy(
                number2 = state.number2 + "."
            )
        }

    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (state.operations) {
                CalculatorOperations.Add -> number1 + number2
                CalculatorOperations.Divide -> number1 / number2
                CalculatorOperations.Multiply -> number1 * number2
                CalculatorOperations.Subtract -> number1 - number2
                null -> return
            }
            state = state.copy(
                number1 = result.toString().take(15), number2 = "",
                operations = null
            )
        }

    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}