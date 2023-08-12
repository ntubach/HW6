package com.javadude.composecalculator

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
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
    /*
    Walk through three sequences of operations - perform more than one + - * / in each sequence
    Be sure to demonstrate the error (check all of the buttons when running to see where the error appears - there is one intentional error in the UI)
     */
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
        var display: String = ""
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
    fun test_compose_ui_calculations() {
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            val display by viewModel.display.collectAsState(initial = "")
            Calculator(
                display = display,
                viewModel = viewModel,
                modifier = Modifier)
        }

        composeTestRule.onNodeWithText("5").assertTextEquals("5").performClick()
        composeTestRule.onNodeWithText("+").assertTextEquals("+").performClick()
        composeTestRule.onNodeWithText("3").assertTextEquals("3").performClick()
        composeTestRule.onNodeWithText("*").assertTextEquals("*").performClick()
        composeTestRule.onNodeWithText("2").assertTextEquals("2").performClick()
        composeTestRule.onNodeWithText("=").assertTextEquals("=").performClick()
        composeTestRule.onNodeWithTag("calculator").assertTextEquals("8.0")
    }
}