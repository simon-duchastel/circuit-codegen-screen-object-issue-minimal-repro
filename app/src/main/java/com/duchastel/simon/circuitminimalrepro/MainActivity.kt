package com.duchastel.simon.circuitminimalrepro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.duchastel.simon.circuitminimalrepro.ui.theme.CircuitMinimalReproTheme
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val circuit = Circuit.Builder().build()

        setContent {
            val backStack = rememberSaveableBackStack(root = CounterScreen)

            CircuitCompositionLocals(circuit) {
                NavigableCircuitContent(
                    navigator = rememberCircuitNavigator(backStack),
                    backStack = backStack,
                )
            }
        }
    }
}

