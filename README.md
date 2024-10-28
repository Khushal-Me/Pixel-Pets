# Virtual Pet Game

## Overview

This project is a virtual pet game designed to be engaging, interactive, and educational for young users aged 7-12. The game allows players to care for and interact with a virtual pet, managing its well-being through feeding, playing, and tending to its needs. The game is set in a fun, visually appealing environment with intuitive controls, designed to foster a sense of responsibility in players while providing a delightful gaming experience. 

---

## Functional Requirements

### User Interface (UI)
- **Graphical User Interface (GUI)**: Fully functional, intuitive, and age-appropriate for children aged 7-12.
- **Player Feedback**: Displays pet's status (hunger, happiness, health, etc.) with real-time responses to user actions.

### Screens
The game will feature five main screens:
1. **Main Menu**:
   - Game title, options for a new game, load game, tutorial, parental controls, and exit.
   - Team credits, term (Fall 2024), and UWO project information.
   - Visual game representation, like an animated pet image.
   
2. **Gameplay Screen**:
   - Shows pet’s stats and allows players to interact via buttons or commands.
   
3. **Tutorial Screen**:
   - Step-by-step gameplay instructions with images and text.

4. **New Game/Pet Selection Screen**:
   - Allows users to select and name their pet.

5. **Parental Controls Screen**:
   - Access is password-protected.
   - Enables setting restrictions and viewing playtime statistics.

### Interaction Methods
- **Mouse-based**: Clickable navigation across menus and gameplay screens.
- **Keyboard-based**: Keyboard shortcuts for common actions (e.g., "F" to feed pet, "P" to pause).

---

## Gameplay and Mechanics

### Pet Interaction
- Players can manage pet stats, including health, hunger, happiness, and sleep, through commands like feeding, playing, and putting the pet to sleep.
- Each pet type has unique behaviors and stat decay rates (e.g., some pets require more frequent feeding).

### Pet States
- Pets may enter special states (e.g., angry, hungry, tired) based on their stats, impacting player actions.

### Save/Load Game State
- **Save Game**: Players can save their game state, preserving pet stats, inventory, and progress.
- **Load Game**: Players can resume a saved game from the main menu.

### Player Inventory
- **Inventory Management**: Displays items such as food and gifts, with each item affecting pet stats differently.
- **Item Usage**: Item count decreases when used, with new items obtainable through in-game progression.

### Pet Sprite and Animation
- Sprite-based visuals reflect the pet's emotions and state (e.g., happy, tired, hungry).
- Simple animations (e.g., blinking, expressions) create a lifelike experience.

### Pet Commands
Players can issue commands to interact with their pet:
- **Feed Pet**: Increases fullness.
- **Play with Pet**: Boosts happiness.
- **Send Pet to Sleep**: Recovers sleep points but restricts interaction temporarily.
- **Take Pet to Vet**: Restores health.
- **Give Gift**: Enhances happiness with specific items.

*Note*: Pets entering critical states (e.g., extreme hunger) will limit available player options.

### Optional Features
- **High Score System**: Tracks and displays top scores based on care performance and positive interaction streaks.
- **Multiplayer Mode**: Allows two players to compare progress and scores in competitive gameplay.

---

## Housekeeping and Error Handling

- **Game State Management**: Save and load functionality for seamless gameplay.
- **UI Scaling**: Adjusts UI elements for different window sizes (optional).
- **Error Handling**: Clear error messages (e.g., saving without disk space).
- **Exit Options**: Players can exit cleanly from any screen.

---

## Authors and Credits
- **Team Number**: Team 07
- **Project Term**: Fall 2024
- **University**: Western University (UWO)

--- 

## License

