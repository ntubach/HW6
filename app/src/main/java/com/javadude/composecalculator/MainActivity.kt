@file:OptIn(ExperimentalMaterial3Api::class)

package com.javadude.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javadude.composecalculator.ui.theme.ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<CalculatorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCalculatorTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val display by viewModel.display.collectAsState(initial = "")
                    Scaffold(
                        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.calculator)) }) },
                        content = { paddingValues ->
                            Calculator(
                                display = display,
                                viewModel = viewModel,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                    )
                }
            }
        }
    }
}

// broken out so we can test just the calculator UI against a view model
@Composable
fun Calculator(
    display: String,
    viewModel: CalculatorViewModel,
    modifier: Modifier,
) = Calculator(
    display = display,
    onClearEntry = viewModel::clearEntry,
    onClear = viewModel::clear,
    onDelete = viewModel::removeDigit,
    onDivide = viewModel::divide,
    onTimes = viewModel::times,
    onSubtract = viewModel::minus,
    onAdd = viewModel::plus,
    onEquals = viewModel::equals,
    onPlusMinus = viewModel::negate,
    onDecimal = viewModel::decimal,
    onNumber = viewModel::addDigit,
    modifier = modifier
)

// so we can test the calculator UI without a view model
@Composable
fun Calculator(
    display: String,
    onClearEntry: () -> Unit,
    onClear: () -> Unit,
    onDelete: () -> Unit,
    onDivide: () -> Unit,
    onTimes: () -> Unit,
    onSubtract: () -> Unit,
    onAdd: () -> Unit,
    onEquals: () -> Unit,
    onPlusMinus: () -> Unit,
    onDecimal: () -> Unit,
    onNumber: (Int) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = display,
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Right,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        CalculatorRow {
            CalculatorButton(stringId = R.string.clear_entry, onClick = onClearEntry)
            CalculatorButton(stringId = R.string.clear, onClick = onClear)
            CalculatorButton(stringId = R.string.delete, onClick = onDelete)
            CalculatorButton(stringId = R.string.divide, onClick = onDivide)
        }
        CalculatorRow {
            CalculatorButton(stringId = R.string.n7, onClick = { onNumber(7) })
            CalculatorButton(stringId = R.string.n8, onClick = { onNumber(8) })
            CalculatorButton(stringId = R.string.n9, onClick = { onNumber(9) })
            CalculatorButton(stringId = R.string.times, onClick = onTimes)
        }
        CalculatorRow {
            CalculatorButton(stringId = R.string.n4, onClick = { onNumber(4) })
            CalculatorButton(stringId = R.string.n5, onClick = { onNumber(5) })
            CalculatorButton(stringId = R.string.n6, onClick = { onNumber(6) })
            CalculatorButton(stringId = R.string.subtract, onClick = onSubtract)
        }
        CalculatorRow {
            CalculatorButton(stringId = R.string.n1, onClick = { onNumber(1) })
            CalculatorButton(stringId = R.string.n2, onClick = { onNumber(1) })
            CalculatorButton(stringId = R.string.n3, onClick = { onNumber(3) })
            CalculatorButton(stringId = R.string.add, onClick = onAdd)
        }
        CalculatorRow {
            CalculatorButton(stringId = R.string.plus_minus, onClick = onPlusMinus)
            CalculatorButton(stringId = R.string.n0, onClick = { onNumber(0) })
            CalculatorButton(stringId = R.string.decimal, onClick = onDecimal)
            CalculatorButton(stringId = R.string.equals, onClick = onEquals)
        }
    }
}

@Composable
fun ColumnScope.CalculatorRow(
    content: @Composable RowScope.() -> Unit
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
    modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
    content = content
)

@Composable
fun RowScope.CalculatorButton(
    @StringRes stringId: Int,
    onClick: () -> Unit
) = CalculatorButton(text = stringResource(id = stringId), onClick = onClick)

@Composable
fun RowScope.CalculatorButton(
    text: String,
    onClick: () -> Unit
) = Box(
    modifier = Modifier
        .clickable(onClick = onClick)
        .padding(4.dp)
        .clip(shape = RoundedCornerShape(8.dp))
        .background(color = Color(100, 181, 246))
        .weight(1f)
        .fillMaxHeight()
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .padding(4.dp)
            .align(Alignment.Center)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeCalculatorTheme {
        Calculator(
            display = "13.45",
            onClearEntry = {},
            onClear = {},
            onDelete = {},
            onDivide = {},
            onTimes = {},
            onSubtract = {},
            onAdd = {},
            onEquals = {},
            onPlusMinus = {},
            onDecimal = {},
            onNumber = {},
            modifier = Modifier
        )
    }
}