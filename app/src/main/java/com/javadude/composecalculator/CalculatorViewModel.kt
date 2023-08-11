package com.javadude.composecalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * The view model - it holds a logic object that the application can use
 *   to perform its actions, and exposes a Flow that represents what should appear on screen
 */
class CalculatorViewModel : ViewModel() {
    private val display0 = MutableStateFlow("")
    val display : Flow<String> = display0
    private val logic = CalculatorLogic {
        display0.value = it
    }

    fun negate() = logic.negate()
    fun equals() = logic.equals()
    fun plus() = logic.plus()
    fun minus() = logic.minus()
    fun times() = logic.times()
    fun divide() = logic.divide()
    fun decimal() = logic.decimal()
    fun addDigit(n: Int) = logic.addDigit(n)
    fun removeDigit() = logic.removeDigit()
    fun clearEntry() = logic.clearEntry()
    fun clear() = logic.clear()
}