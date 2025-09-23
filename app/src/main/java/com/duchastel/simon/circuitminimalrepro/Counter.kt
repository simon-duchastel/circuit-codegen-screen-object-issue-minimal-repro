package com.duchastel.simon.circuitminimalrepro

import android.os.Parcelable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import kotlinx.parcelize.Parcelize

@Parcelize
data object CounterScreen : Screen, Parcelable

class CounterPresenter @AssistedInject constructor(
    @Assisted navigator: Navigator,
    @Assisted screen: CounterScreen,
): Presenter<CounterUiState> {
    @Composable
    override fun present(): CounterUiState {
        var count by rememberSaveable { mutableIntStateOf(0) }

        return CounterUiState(
            count = count,
            eventSink = { event ->
                when (event) {
                    CounterUiEvent.Increment -> count++
                    CounterUiEvent.Decrement -> count--
                }
            }
        )
    }

    @CircuitInject(CounterScreen::class, SingletonComponent::class)
    @AssistedFactory
    fun interface Factory  {
        fun create(screen: CounterScreen, navigator: Navigator): CounterPresenter
    }
}

data class CounterUiState(
    val count: Int,
    val eventSink: (CounterUiEvent) -> Unit
) : CircuitUiState

sealed interface CounterUiEvent : CircuitUiEvent {
    data object Increment : CounterUiEvent
    data object Decrement : CounterUiEvent
}

@CircuitInject(CounterScreen::class, SingletonComponent::class)
@Composable
fun CounterUi(
    state: CounterUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Count: ${state.count}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { state.eventSink(CounterUiEvent.Decrement) }
            ) {
                Text("-")
            }
            
            Button(
                onClick = { state.eventSink(CounterUiEvent.Increment) }
            ) {
                Text("+")
            }
        }
    }
}