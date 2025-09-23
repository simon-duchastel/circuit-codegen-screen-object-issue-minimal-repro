package com.duchastel.simon.circuitminimalrepro

import com.slack.circuit.runtime.presenter.Presenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.codegen.OriginatingElement
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
@OriginatingElement(topLevelClass = CounterPresenter::class)
public abstract class CounterPresenterFactoryModule {
  @Binds
  @IntoSet
  public abstract fun bindCounterPresenterFactory(counterPresenterFactory: CounterPresenterFactory): Presenter.Factory
}
