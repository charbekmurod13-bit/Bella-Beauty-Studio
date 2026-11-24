package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.example.navigation.BellaNavGraph
import com.example.ui.theme.BellaBeautyTheme
import com.example.ui.theme.BellaIvory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                BellaIvory.toArgb(),
                BellaIvory.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                BellaIvory.toArgb(),
                BellaIvory.toArgb()
            )
        )
        setContent {
            BellaBeautyTheme {
                BellaNavGraph()
            }
        }
    }
}
