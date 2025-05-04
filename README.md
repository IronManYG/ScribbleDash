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

**ScribbleDash** is an Android app designed to let you freely draw on a digital canvas while incorporating fun game modes. Whether you want to doodle casually or challenge yourself with specific modes, you’ll have access to intuitive undo/redo controls, a clean canvas, and a simple UI. Also its lets you draw on a digital canvas and compete in fast **memory‑sketch** rounds. With **Milestone 2** the project evolves from a sandbox into a fully scored game loop:

1. Pick **One Round Wonder** on the Home screen.
2. Choose a difficulty (brush thickness scales with difficulty).
3. Memorize the reference image shown during a 3‑second countdown.
4. Redraw the image from memory.
5. Tap **Done**—a pixel‑by‑pixel comparison algorithm rates your accuracy.
6. View your score, read playful feedback, and dive right back in.

## Features

### Home Screen

* App title and list of available modes.
* Bottom navigation with **Home** and **Statistics**.

### Statistics Screen *(new in Milestone 2)*

* Static mock‑up that will evolve into full leaderboards.
* Placeholder cards for score history and accuracy distribution.

### Game Modes

| Mode                 | Highlights in Milestone 2                                                                                                                                                                                                                                                                                            |
| -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **One Round Wonder** | • Difficulty picker (Beginner ×15, Challenging ×7, Master ×4).• **Preview**—fullscreen reference with “Ready, set …” countdown.• **Draw Mode**—1 : 1 grid canvas, undo/redo stack (×5), **Done** button.• **Results**—side‑by‑side canvases, score %, rating badge (Oops → Woohoo!), random feedback, **Try Again**. |

> Additional modes will live under `features/gamemodes/*` without impacting existing code.

### Drawing Canvas

* Fixed 1 : 1 surface with a nine‑square grid background.
* Default stroke width = 4 dp (scaled only for comparison).
* Undo ⟲ / Redo ⟳ (up to 5), Clear, and **Done**.
* Close icon returns to Home—system‑back is disabled on the Results screen.

### Accuracy Algorithm

```text
Final Score (%) = Coverage (%) − MissingLengthPenalty (%)
```

1. Parse SVGs into `Path` data (cached on app start).
2. Clone example paths with a thicker stroke based on difficulty.
3. Normalize both drawings (translate to origin, scale to canvas).
4. Rasterize to two transparent bitmaps.
5. Coverage = matching pixels ÷ visible user pixels.
6. If user‑path length < 70 % of example, deduct the missing percent.

| Score  | Rating  |
| ------ | ------- |
| 0–39   | Oops    |
| 40–69  | Meh     |
| 70–79  | Good    |
| 80–89  | Great   |
| 90–100 | Woohoo! |

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
