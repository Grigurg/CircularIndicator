package com.grig.circularindicator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.grig.circularindicator.ui.theme.CircularIndicatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CircularIndicatorTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var value by remember {
                        mutableStateOf(0)
                    }

                    var text by remember {
                        mutableStateOf("")
                    }

                    CustomComponent(
                        indicatorValue = value,
                        duration = 2000,
                        foregroundIndicatorStrokeWidth = 50f,
//                        foregroundIndicatorColor =
                    )
                    TextField(
                        value = text,
                        onValueChange = {
                            if (!it.isDigitsOnly()) return@TextField
                            value = if (it.isNotEmpty()) it.toInt() else 0
                            text = it.ifEmpty {""}
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
        }
    }
}