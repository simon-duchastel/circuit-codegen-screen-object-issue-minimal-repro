# Circuit codegen issue when Screen is an object - minimal repro

This repository demonstrates a minimal reproduction of a Circuit codegen issue when using `data object` screens and Circuit codegen for presenters (ie. assisted factories). The issue specifically appears to be new in K2 - in K2 when statements that use a non-`is` check for objects don't smart cast the type. 

## Error Details

- **Build Error:** `e: file:///path/to/CounterPresenterFactory.kt:17:46 Argument type mismatch: actual type is 'Screen', but 'CounterScreen' was expected.`
- **Generated Code:** [CounterPresenterFactory.kt](fails-with-k2/app/build/generated/ksp/debug/kotlin/com/duchastel/simon/circuitminimalrepro/CounterPresenterFactory.kt#L17)
- **Presenter:** [CounterPresenter](fails-with-k2/app/src/main/java/com/duchastel/simon/circuitminimalrepro/Counter.kt#L33)

## Reproduction Steps

1. Clone this repository
2. Run `./gradlew assembleDebug`
3. Observe the compilation error in the generated `CounterPresenterFactory.kt`
