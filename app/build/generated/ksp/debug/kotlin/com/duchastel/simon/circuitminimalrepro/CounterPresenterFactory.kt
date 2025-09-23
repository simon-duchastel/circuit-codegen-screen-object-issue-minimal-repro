package com.duchastel.simon.circuitminimalrepro

import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import javax.inject.Inject

public class CounterPresenterFactory @Inject constructor(
  private val factory: CounterPresenter.Factory,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? = when (screen) {
    CounterScreen -> factory.create(screen = screen, navigator = navigator)
    else -> null
  }
}
