package com.javadude.composecalculator

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ViewModelTests {
    private val viewModel = CalculatorViewModel()

    // add your view model tests here
    @Test
    fun test_view_model_plus() {
        runBlocking {
            viewModel.addDigit(1)
            viewModel.plus()
            viewModel.addDigit(2)
            viewModel.equals()
            viewModel.display.test {
                assertEquals("3.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun negate() {
        logic.addDigit(1)
        logic.negate()
        assertEquals("-1", display)
        logic.negate()
        assertEquals("1", display)
        logic.plus()
        logic.addDigit(2)
        logic.negate()
        logic.equals()
        assertEquals("-1.0", display)
    }

    @Test
    fun testEquals() {
        // Display starts empty, equals sets to 0.0
        assertEquals("", display)
        logic.equals()
        assertEquals("0.0", display)
        // Adding digits 101 then equals displays 101.0
        logic.addDigit(1)
        logic.addDigit(0)
        logic.addDigit(1)
        logic.equals()
        assertEquals("101.0", display)
        // Adding digits 101 . 10 then equals displays 101.1
        logic.addDigit(1)
        logic.addDigit(0)
        logic.addDigit(1)
        logic.decimal()
        logic.addDigit(1)
        logic.addDigit(0)
        logic.equals()
        assertEquals("101.1", display)
    }
    @Test
    fun minus() {
        logic.addDigit(3)
        logic.minus()
        logic.addDigit(2)
        logic.equals()
        assertEquals("1.0", display)
    }

    @Test
    fun times() {
        logic.addDigit(3)
        logic.times()
        logic.addDigit(2)
        logic.equals()
        assertEquals("6.0", display)
    }

    @Test
    fun divide() {
        logic.addDigit(6)
        logic.divide()
        logic.addDigit(3)
        logic.equals()
        assertEquals("2.0", display)
    }

    @Test
    fun decimal() {
        assertEquals("", display)
        logic.decimal()
        assertEquals("", display)
        logic.addDigit(0)
        assertEquals("0", display)
        logic.decimal()
        assertEquals("0.", display)
        for (it in 0 .. 9) {
            logic.addDigit(it)
        }
        assertEquals("0.0123456789", display)
        logic.equals()
        assertEquals("0.0123456789", display)
    }

    @Test
    fun addDigit() {
        for (it in 9 downTo 0) {
            logic.addDigit(it)
        }
        assertEquals("9876543210", display)
    }

    @Test
    fun removeDigit() {
        logic.addDigit(1)
        logic.removeDigit()
        assertEquals("0.0", display)
        logic.clear()
        for (it in 9 downTo 0) {
            logic.addDigit(it)
        }
        logic.removeDigit()
        logic.removeDigit()
        assertEquals("98765432", display)
    }

    @Test
    fun clearEntry() {
        logic.addDigit(1234)
        logic.clearEntry()
        assertEquals("0.0", display)
        logic.clearEntry()
        logic.addDigit(1)
        logic.plus()
        logic.addDigit(1)
        logic.clearEntry()
        assertEquals("0.0", display)
        logic.addDigit(2)
        logic.equals()
        assertEquals("3.0", display)
    }

    @Test
    fun clear() {
        logic.addDigit(1234)
        logic.clear()
        assertEquals("0.0", display)
        logic.clearEntry()
        logic.addDigit(1)
        logic.plus()
        logic.addDigit(1)
        logic.clear()
        assertEquals("0.0", display)
        logic.addDigit(2)
        logic.decimal()
        logic.addDigit(2)
        logic.equals()
        assertEquals("2.2", display)
    }
}