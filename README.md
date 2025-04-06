# ScribbleDash

<p align="center">
  A playful drawing app that lets you unleash your inner artist on a virtual canvas. Experiment with brushes, game modes, undo/redo features, and more!
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Min%20SDK-25-blue.svg" alt="Min SDK">
  <img src="https://img.shields.io/badge/Target%20SDK-34-brightgreen.svg" alt="Target SDK">
  <img src="https://img.shields.io/badge/Kotlin-2.0.20-purple.svg" alt="Kotlin Version">
  <img src="https://img.shields.io/badge/Jetpack%20ComposeBom-2025.02.00-orange.svg" alt="Jetpack Compose Version">
</p>

## Overview

**ScribbleDash** is an Android app designed to let you freely draw on a digital canvas while incorporating fun game modes. Whether you want to doodle casually or challenge yourself with specific modes, you’ll have access to intuitive undo/redo controls, a clean canvas, and a simple UI. The initial milestone focuses on a single mode (One Round Wonder), but this README anticipates an expandable architecture to accommodate future modes.

## Features

-   **Home Screen**
    -   Displays app title and available game modes (with placeholders for future modes)
    -   Bottom navigation bar (one tab for Home, another reserved for future milestones)
-   **Game Modes**
    -   **One Round Wonder**
        -   Difficulty selection screen (Beginner, Challenging, Master)
        -   Tapping any difficulty sends you straight into the drawing canvas
-   **Drawing Canvas**
    -   1:1 aspect ratio canvas with grid background
    -   **Undo/Redo** up to 5 recent strokes
    -   **Clear Canvas** to remove all paths
    -   Close icon to return to the Home screen
-   **Future Navigation**
    -   Additional modes will slot into a dedicated `gamemodes` package structure
    -   Potential for advanced drawing features, timed challenges, or multiplayer modes

## Screenshots

| Screen              | Preview                             |
| :------------------ | :---------------------------------- |
| **Home Screen** | ![Home Preview](previews/Home.png)  |
| **Difficulty Screen** | ![Difficulty](previews/Difficulty.png)|
| **Drawing Canvas** | ![Drawing](previews/Drawing.png)    |

## Tech Stack & Libraries

-   **Minimum SDK**: 25
-   **Kotlin + Coroutines + Flow**: Modern concurrency & reactive streams
-   **Jetpack Compose**: Declarative UI toolkit
-   **Navigation Compose**: Simple screen transitions
-   **Material Design 3**: Consistent theming & components
-   **Dependency Injection**: Koin for managing dependencies

## Architecture

While this is a single-module project, the folder structure is inspired by a clean, modular approach:

```
scribbledash/
├── app/
├── core/
│   ├── data/            // Shared data logic or repositories
│   ├── domain/          // Core business models, common interfaces
│   └── presentation/    // Base Compose components, theming, UI utilities
├── features/
│   ├── home/
│   │   ├── data/
│   │   ├── domain/
│   │   └── presentation/
│   ├── gamemodes/
│   │   ├── onewonder/
│   │   │   ├── data/
│   │   │   ├── domain/
│   │   │   └── presentation/
│   │   └── (future_mode)/
│   │       ├── data/
│   │       ├── domain/
│   │       └── presentation/
│   └── draw/
│       ├── data/
│       ├── domain/
│       └── presentation/
└── utils/
```


This layout keeps each feature self-contained (`data`, `domain`, `presentation`) and ready for easier extraction into separate Gradle modules in the future.

### Why This Setup?

-   **Feature Isolation**: New game modes slot under `features/gamemodes`, each with clear boundaries for logic and UI.
-   **Clear Layer Separation**: Even in a single module, you’re guided by data/domain/presentation layers.
-   **Scalability**: You can promote each feature to its own module if complexity grows.

## Getting Started

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/IronManYG/ScribbleDash.git](https://github.com/IronManYG/ScribbleDash.git)
    ```
2.  **Open in Android Studio**
3.  **Sync Gradle and build the project**
4.  **Run on an emulator or a physical device**

## Acknowledgment

This project was built as part of the **[Pl Mobile Dev Campus](https://pl-coding.com/campus/)** community challenge. 
If you have any questions or suggestions, please get in touch with me or open an issue.

Happy Scribbling!
