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
    fun test_view_model_negate() {
        runBlocking {
            viewModel.addDigit(1)
            viewModel.negate()
            viewModel.display.test {
                assertEquals("-1", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.negate()
            viewModel.display.test {
                assertEquals("1", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.plus()
            viewModel.addDigit(2)
            viewModel.negate()
            viewModel.equals()
            viewModel.display.test {
                assertEquals("-1.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun test_view_model_testEquals() {
        runBlocking {
            // Display starts empty, equals sets to 0.0
            viewModel.equals()
            viewModel.display.test {
                assertEquals("0.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            // Adding digits 101 then equals displays 101.0
            viewModel.addDigit(1)
            viewModel.addDigit(0)
            viewModel.addDigit(1)
            viewModel.equals()
            viewModel.display.test {
                assertEquals("101.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            // Adding digits 101 . 10 then equals displays 101.1
            viewModel.addDigit(1)
            viewModel.addDigit(0)
            viewModel.addDigit(1)
            viewModel.decimal()
            viewModel.addDigit(1)
            viewModel.addDigit(0)
            viewModel.equals()
            viewModel.display.test {
                assertEquals("101.1", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun test_view_model_minus() {
        runBlocking {
            viewModel.addDigit(3)
            viewModel.minus()
            viewModel.addDigit(2)
            viewModel.equals()
            viewModel.display.test {
                // This test will fail due to minus being mapped to plus
                assertEquals("1.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun test_view_model_times() {
        runBlocking {
            viewModel.addDigit(3)
            viewModel.times()
            viewModel.addDigit(2)
            viewModel.equals()
            viewModel.display.test {
                assertEquals("6.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun test_view_model_divide() {
        runBlocking {
            viewModel.addDigit(6)
            viewModel.divide()
            viewModel.addDigit(3)
            viewModel.equals()
            viewModel.display.test {
                assertEquals("2.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun test_view_model_decimal() {
        runBlocking {
            viewModel.decimal()
            viewModel.display.test {
                assertEquals("", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.addDigit(0)
            viewModel.display.test {
                assertEquals("0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.decimal()
            viewModel.display.test {
                assertEquals("0.", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            for (it in 0..9) {
                viewModel.addDigit(it)
            }
            viewModel.display.test {
                assertEquals("0.0123456789", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.equals()
            viewModel.display.test {
                assertEquals("0.0123456789", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun test_view_model_addDigit() {
        runBlocking {
            for (it in 9 downTo 0) {
                viewModel.addDigit(it)
            }
            viewModel.display.test {
                assertEquals("9876543210", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun test_view_model_removeDigit() {
        runBlocking {
            viewModel.addDigit(1)
            viewModel.removeDigit()
            viewModel.display.test {
                assertEquals("0.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.clear()
            for (it in 9 downTo 0) {
                viewModel.addDigit(it)
            }
            viewModel.removeDigit()
            viewModel.removeDigit()
            viewModel.display.test {
                // This test will fail due to removeDigit removing two digits, not one
                assertEquals("98765432", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun test_view_model_clearEntry() {
        runBlocking {
            viewModel.addDigit(1234)
            viewModel.clearEntry()
            viewModel.display.test {
                assertEquals("0.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.clearEntry()
            viewModel.addDigit(1)
            viewModel.plus()
            viewModel.addDigit(1)
            viewModel.clearEntry()
            viewModel.display.test {
                assertEquals("0.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.addDigit(2)
            viewModel.equals()
            viewModel.display.test {
                assertEquals("3.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun test_view_model_clear() {
        runBlocking {
            viewModel.addDigit(1234)
            viewModel.clear()
            viewModel.display.test {
                assertEquals("0.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.clearEntry()
            viewModel.addDigit(1)
            viewModel.plus()
            viewModel.addDigit(1)
            viewModel.clear()
            viewModel.display.test {
                assertEquals("0.0", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.addDigit(2)
            viewModel.decimal()
            viewModel.addDigit(2)
            viewModel.equals()
            viewModel.display.test {
                assertEquals("2.2", awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}