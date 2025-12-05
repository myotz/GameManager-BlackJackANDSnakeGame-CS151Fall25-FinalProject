## Game Manager: Blackjack & Snake
Members:  Myo Thant Zin, Shu Saw, Chan Li, Kenrick Doan

San José State University – CS 151 Project 2

## Overview

This project implements a JavaFX Game Manager that supports two playable games:
- Blackjack (mouse input only)
- Snake (keyboard input only)

It follows Object-Oriented Programming (OOP) principles, including encapsulation, inheritance, and polymorphism.  
The system allows players to log in / create accounts, track high scores, and save game progress.

## Features

### Game Manager
- User **login and account creation** 
- Persistent **high score tracking** for both games
- Centralized **main menu launcher**
- **Toolbar** visible across all scenes (Main Menu, Blackjack, Snake)
- Handles file storage and session persistence

### Blackjack
- 1 **human player**, 2 **AI opponents**, and 1 **Dealer**
- Supports:
  - Betting system  
  - Hit / Stand logic  
  - Dealer rules (hits on soft 17)
  - Win/loss money calculation  
- **Save & Load game state** using a copyable string  
- Full **graphical interface** built with JavaFX  
- Only **mouse input** required  

### Snake
- Classic Snake game built in JavaFX  
- Controlled via keyboard arrow keys  
- Food spawning, snake growth, and collision detection  
- **Pause** and **Restart** functions  
- Real-time score tracking and persistent high scores


## Object-Oriented Design

| Principle | Application |
|------------|-------------|
| **Encapsulation** | Player, Deck, Snake each control their internal logic via methods. |
| **Abstraction** | Classes separate responsibilities — `BlackjackGame` handles controller logic, `MainApp` handles entry point for starting games. |
| **Inheritance** | `Dealer` extends `Player`; both share betting and hand logic. |
| **Polymorphism** | AI and Human players respond differently to `hit()` / `stand()` decisions. |

## Team Contributions
- Myo Thant Zin:
    - Developed Game Manager system, including login, account creation, encrypted user data, high score persistence, main menu, and scene transitions.
    - Implemented audio management system, including background music, sound effects, fixing overlapping audio, and global toolbar volume slider integration.
    - Built or refined large portions of the Blackjack UI, Snake UI, and overall application visual consistency.
    - Added save/load system for Blackjack through GameSaveHandler and later improvements.
    - Implemented encryption for user data and high scores (extra credit).
    - Continuously refined UI sizing, alignment, and visual polish across all screens.
    - Maintained repository structure, fixed merge conflicts, removed unused files, and ensured smooth builds.

- Shu Saw:
    - Created Snake game visual assets such as apple, pear, banana, and implemented them into the Snake food spawning logic.
    - Added gameplay effects for Snake, improving feedback when food is eaten or game state changes.
    - Implemented Snake audio system, including sound effects and background music.
    - Moved Snake volume controls into the global toolbar so audio settings apply across the entire application.
    - Added multiple Snake-focused fixes such as pause message cleanup, sound configuration logic, and visual polishing.

- Chan Li:
    - Implemented core Blackjack data model classes, including Hand, Deck, Player, Dealer, Suit, Rank, and the early structure of the Blackjack engine.
    - Designed and implemented the Blackjack GameState, which tracks players, dealer, deck, round data, and game phases.
    - Added and integrated placeBet() logic inside the controller, including validation, AI bet logic, and phase transitions.
    - Implemented Blackjack sound effects (card dealing, busting, winning, etc.) and integrated them into the controller and UI.
    - Added helper functions for updating Blackjack UI state and triggering audio events during gameplay.

- Kenrick Doan:
    - Set up JUnit testing infrastructure and reorganized the project structure to support test automation (extra credit work).
    - Added Maven build support by creating pom.xml and adjusting project layout to follow standard Maven conventions.
    - Performed major restructuring to clean and prepare files for testing, simplifying long-term maintenance.
    - Contributed early Blackjack structure and foundational classes during initial project phase.
    - Helped resolve multiple merge conflicts and reorganization-related issues.


## Installation

### Prerequisites
- **Java 17+**
- **JavaFX SDK** (must be on classpath)
- Optional: **Gradle** or **Maven** (build automation)

### Clone the Repository
```bash
git clone https://github.com/myotz/GameManager-BlackJackANDSnakeGame-CS151Fall25-FinalProject.git
```

