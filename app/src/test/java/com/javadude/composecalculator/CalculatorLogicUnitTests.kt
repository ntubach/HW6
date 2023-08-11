package com.javadude.composecalculator

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculatorLogicUnitTests {
    private var display = ""
    private val logic = CalculatorLogic { display = it }
    // add your unit tests here

    @Test
    fun negate() {
        logic.addDigit(1)
        logic.negate()
        assertEquals("-1.0", display)
        logic.negate()
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
    fun plus() {
        logic.addDigit(1)
        logic.plus()
        logic.addDigit(2)
        logic.equals()
        assertEquals("3.0", display)
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
    }

    @Test
    fun clearEntry() {
    }

    @Test
    fun clear() {
    }

    @Test
    fun getOnDisplayChanged() {
    }

    @Test
    fun check_buttons() {

    }


}