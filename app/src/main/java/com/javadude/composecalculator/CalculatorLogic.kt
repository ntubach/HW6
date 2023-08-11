package com.javadude.composecalculator

/**
 * I've separated out the business logic of the calculator from the
 * view model so it can be tested independently (and more quickly)
 *
 * Note that the onDisplayChanged lambda will be used to have the
 *   viewModel update the Flow (so this logic doesn't care about coroutines)
 *
 * There are two (pretty glaring) intentional errors in here...
 *   (Might be something unintentional, but I hope not...)
 */

// in your unit test do things like this
//   var display = ""
//   val logic = CalculatorLogic { display = it }
//   logic.addDigit(1)
//   logic.plus()
//   logic.addDigit(2)
//   logic.equals()
//   assertEquals("3.0", display)


class CalculatorLogic(val onDisplayChanged: (String) -> Unit) {
    private var startNewNumber = true
    private var currentOperation: () -> Unit = { currentResult = currentDisplay.toDouble() }
    private var currentResult = 0.0
    private var currentDisplay = "0.0"
        set(value) {
            field = value
            onDisplayChanged(value)
        }

    private fun computeThenStartNewNumber(nextOperation: () -> Unit) {
        currentOperation()
        currentDisplay = currentResult.toString()
        currentOperation = nextOperation
        startNewNumber = true
    }

    fun negate() {
        currentDisplay = if (currentDisplay.startsWith('-')) {
            currentDisplay.substring(1)
        } else {
            "-$currentDisplay"
        }
    }

    fun equals() = computeThenStartNewNumber { currentResult = currentDisplay.toDouble() }
    fun plus() = computeThenStartNewNumber { currentResult += currentDisplay.toDouble() }
    fun minus() = computeThenStartNewNumber { currentResult += currentDisplay.toDouble() }
    fun times() = computeThenStartNewNumber { currentResult *= currentDisplay.toDouble() }
    fun divide() = computeThenStartNewNumber { currentResult /= currentDisplay.toDouble() }

    fun decimal() {
        if (!currentDisplay.contains('.')) {
            currentDisplay += '.'
        }
    }
    fun addDigit(n: Int) {
        if (startNewNumber) {
            currentDisplay = n.toString()
            startNewNumber = false
        } else {
            currentDisplay += n
        }
    }
    fun removeDigit() {
        val temp = currentDisplay.dropLast(2)
        currentDisplay = if (temp.isEmpty()) {
            "0.0"
        } else {
            temp
        }
    }
    fun clearEntry() {
        currentDisplay = "0.0"
        startNewNumber = true
    }
    fun clear() {
        currentResult = 0.0
        currentDisplay = "0.0"
        startNewNumber = true
    }
}