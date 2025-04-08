# DailySnap

A minimal social app prototype. Built around daily prompts and authentic photo sharing.

[Project description](https://github.com/user-attachments/files/19658233/project.description.pdf)

https://github.com/user-attachments/assets/e8b092ef-b6db-4e9f-a700-6b2fdd52c65b


## Features

- Daily prompt question
- Full-screen camera with preview and comment
- Feed of mock friend responses (profile image, username, main photo)
- Simple back stack navigation
- Dark mode support

## Architecture

The app follows the MVI (Model–View–Intent) architecture.

- UI (Jetpack Compose)
- ViewModel (state, intent, logic)
- UseCases (business logic)
- Repository (mocked data)

### State Management

- `StateFlow` is used to expose state from ViewModels
- Intents are dispatched from UI to ViewModels
- UseCases encapsulate business logic
- [Molecule](https://github.com/cashapp/molecule) is used to create reactive state from Compose functions and tie them to ViewModels


### ViewModel Structure

Each ViewModel extends a base class with:

```kotlin
val viewStates: StateFlow<ViewState>
fun handle(intent: Intent)
```

## Testing

Unit tests use [Turbine](https://github.com/cashapp/turbine) to verify `StateFlow` emissions.

```kotlin
@Test
fun `emits prompt on LoadPrompt intent`() = runTest {
    viewModel.handle(LoadPrompt)
    viewModel.viewStates.test {
        assertThat(awaitItem().userPrompt).isEqualTo("What made you smile today?")
    }
}
```
Testability is ensured by:

- Separated use cases and state reducers
- Dependency injection via Hilt
- Injectable time sources for time-sensitive logic

## Tech Stack

- Kotlin, Coroutines, Flow
- Molecule
- Jetpack Compose
- CameraX
- Hilt
- Coil
- Turbine
