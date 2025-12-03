## Game Manager: Blackjack & Snake
Members:  Myo Thant Zin, Shu Saw, Chan Li, Kenrick Doan

San José State University – CS 151 Project 2

Overview

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

---

## Installation

### Prerequisites
- **Java 17+**
- **JavaFX SDK** (must be on classpath)
- Optional: **Gradle** or **Maven** (build automation)

### Clone the Repository
```bash
git clone https://github.com/myotz/GameManager-BlackJackANDSnakeGame-CS151Fall25-FinalProject.git
```

