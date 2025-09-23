package com.duchastel.simon.circuitminimalrepro

import com.slack.circuit.runtime.ui.Ui
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
public abstract class CounterUiFactoryModule {
  @Binds
  @IntoSet
  public abstract fun bindCounterUiFactory(counterUiFactory: CounterUiFactory): Ui.Factory
}
