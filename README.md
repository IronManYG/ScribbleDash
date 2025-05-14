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

**ScribbleDash** is an Android app that began as a simple drawing playground and has grown—by **Milestone 3**—into a mini‑arcade of sketch‑based challenges. Whether you doodle casually or battle the clock, you’ll find intuitive undo/redo controls, a clean canvas, and three distinct game modes:

* **One Round Wonder** – one drawing, one score.
* **Speed Draw** – cram as many decent sketches as you can into two minutes.
* **Endless Mode** – keep going until your last drawing falls below *Good* quality.

## Features

### Home Screen&#x20;

* App title and  List of game‑mode cards (**One Round Wonder, Speed Draw, Endless Mode**).
* Bottom navigation with **Home** and **Statistics**.

### Statistics Screen *(dynamic in Milestone 3)*

* Shows personal bests collected across sessions:

  * Highest **Speed Draw** accuracy %.
  * Most Meh+ drawings in **Speed Draw**.
  * Highest **Endless Mode** accuracy %.
  * Most drawings completed in **Endless Mode**.


### Game Modes

| Mode                 | Core Loop & Highlights                                                                                                                                                                                                                                                                                                                                          |
| -------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **One Round Wonder** | *Pick difficulty → 3‑sec preview → draw from memory → see score & feedback.* Same flow as Milestone 2, still the yard‑stick for single‑shot accuracy.                                                                                                                                                                                                           |
| **Speed Draw**       | *2‑minute timer* (pauses while preview is shown). Counter increments when a finished drawing scores **Meh (≥40 %)** or better. Instant background comparison so the game never stutters. When time’s up you see an averaged accuracy %, the total valid drawings, and shiny **New High Score** banners if you beat yourself. **Draw Again!** restarts the mode. |
| **Endless Mode**     | Draw until you stumble: each finished sketch must score **Good (≥70 %)** or better. Results screen shows green ✓ / red ✗ indicator. **Next Drawing** keeps the streak alive; **Finish** ends the run and shows a *Game over!* summary with average accuracy and total drawings.  

### Drawing Canvas (shared mechanics)

* 1 : 1 grid canvas with rounded corners.
* Stroke width = 4 dp (scaled internally for scoring).
* **Undo / Redo** up to 5 strokes.
* **Clear** (or **Done** in draw‑modes).
* Close icon returns to Home; system‑back is disabled on result screens.
* **Speed Draw** overlay: visible round timer (turns red ≤ 30 s) and completed‑drawings counter.

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
| **Speed Draw – Timer**    |         |
| **Endless Mode – Result** |         |

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
│   │   ├── speeddraw/
│   │   └── endless/
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

