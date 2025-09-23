package com.duchastel.simon.circuitminimalrepro

import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import javax.inject.Inject

public class CounterUiFactory @Inject constructor() : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    CounterScreen -> ui<CounterUiState> { state, modifier -> CounterUi(state = state, modifier = modifier) }
    else -> null
  }
}
