# Circuit Codegen Issue with Data Objects and K2 Compiler

This repository demonstrates a minimal reproduction of a Circuit codegen issue when using `data object` screens with the Kotlin K2 compiler.

## The Issue

When Circuit's codegen processes a `data object` that implements `Screen`, it generates code that uses object pattern matching (`CounterScreen ->`) instead of type checking (`is CounterScreen`) in `when` expressions. This prevents the Kotlin K2 compiler from properly smartcasting the `screen` parameter, leading to compilation errors.

## Error Details

**Build Error:**
```
e: file:///path/to/CounterPresenterFactory.kt:17:46 Argument type mismatch: actual type is 'Screen', but 'CounterScreen' was expected.
```

**Root Cause:**
- **Source:** `CounterScreen` is defined as `data object CounterScreen : Screen, Parcelable` in `Counter.kt:33`
- **Generated Code:** Circuit codegen creates `CounterScreen ->` in the `when` expression at `CounterPresenterFactory.kt:17`
- **Problem:** The K2 compiler doesn't smartcast `screen` from `Screen` to `CounterScreen` with object pattern matching
- **Result:** `factory.create(screen = screen, navigator = navigator)` fails because `screen` is still typed as `Screen`

## Code Analysis

### Source Code (`Counter.kt`)
```kotlin
@Parcelize
data object CounterScreen : Screen, Parcelable
```

### Generated Code (`CounterPresenterFactory.kt`)
```kotlin
override fun create(
  screen: Screen,
  navigator: Navigator,
  context: CircuitContext,
): Presenter<*>? = when (screen) {
  CounterScreen -> factory.create(screen = screen, navigator = navigator) // ❌ screen is still Screen type
  else -> null
}
```

### Expected Fix
The codegen should generate:
```kotlin
is CounterScreen -> factory.create(screen = screen, navigator = navigator) // ✅ screen is smartcast to CounterScreen
```

## Environment

- **Kotlin:** 2.2.10 (K2 compiler)
- **Circuit:** 0.30.0
- **Android Gradle Plugin:** 8.10.1
- **KSP:** 2.2.10-2.0.2

## Reproduction Steps

1. Clone this repository
2. Run `./gradlew assembleDebug`
3. Observe the compilation error in the generated `CounterPresenterFactory.kt`

## Project Structure

```
app/src/main/java/com/duchastel/simon/circuitminimalrepro/
├── Counter.kt                     # Contains CounterScreen data object
├── MainActivity.kt
└── ui/theme/                      # Theme files

app/build/generated/ksp/debug/kotlin/com/duchastel/simon/circuitminimalrepro/
├── CounterPresenterFactory.kt     # ❌ Contains the problematic generated code
├── CounterUiFactory.kt
└── ...
```

## Key Files

- `Counter.kt:33` - Defines `CounterScreen` as a data object
- `CounterPresenterFactory.kt:17` - Generated code with the smartcast issue
- `app/build.gradle.kts:47` - Circuit codegen configuration

## Impact

This issue prevents using `data object` screens with Circuit when targeting the K2 compiler, forcing developers to use regular classes instead of the more appropriate `data object` pattern for singleton screen types.