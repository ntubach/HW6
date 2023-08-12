package com.javadude.composecalculator

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    // add your instrumented tests here
    @Test
    fun test_compose_ui_numbers() {
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            val display by viewModel.display.collectAsState(initial = "")
            Calculator(
                display = display,
                viewModel = viewModel,
                modifier = Modifier)
        }

        // Test for each number button being clicked
        var display = ""
        for (i in 0..9) {
             display += i.toString()
            composeTestRule
                .onNodeWithText(i.toString())
                .assertIsDisplayed().performClick()
            composeTestRule
                .onNodeWithTag("calculator")
                .assertTextEquals(display)
        }
    }

    @Test
    fun test_compose_ui_calculation_1() {
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            val display by viewModel.display.collectAsState(initial = "")
            Calculator(
                display = display,
                viewModel = viewModel,
                modifier = Modifier)
        }

        val test1 = "5+3*3=" // Should equal '24'
        val result1 = "24.0"
        test1.forEach {composeTestRule.onNodeWithText(it.toString()).assertTextEquals(it.toString()).performClick()}
        composeTestRule.onNodeWithTag("calculator").assertTextEquals(result1)
    }

    @Test
    fun test_compose_ui_calculation_2() {
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            val display by viewModel.display.collectAsState(initial = "")
            Calculator(
                display = display,
                viewModel = viewModel,
                modifier = Modifier)
        }

        val test2 = "3406/13-161=" // Should equal '101'
        val result2 = "101.0"
        test2.forEach {composeTestRule.onNodeWithText(it.toString()).assertTextEquals(it.toString()).performClick()}
        composeTestRule.onNodeWithTag("calculator").assertTextEquals(result2)
    }

    @Test
    fun test_compose_ui_calculation_3() {
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            val display by viewModel.display.collectAsState(initial = "")
            Calculator(
                display = display,
                viewModel = viewModel,
                modifier = Modifier)
        }

        val test3 = "5+5+5+5+5*5-5/5=" // Should equal '24'
        val result3 = "24.0"
        test3.forEach {composeTestRule.onNodeWithText(it.toString()).assertTextEquals(it.toString()).performClick()}
        composeTestRule.onNodeWithTag("calculator").assertTextEquals(result3)
    }
}