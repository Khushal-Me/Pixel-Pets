# Pixel Pets

**Pixel Pets** is a virtual pet simulation game where players can adopt and care for different types of pets (Dog, Cat, Bird). The game features an interactive user interface and gameplay mechanics that allow players to monitor their pet's stats and keep them happy and healthy.

---

## Project Structure

```plaintext
.
├── README.md       # Documentation file
├── out/            # Compiled class files
├── saves/          # Save game files
├── src/            # Source code
│   └── META-INF/   # Manifest file
└── test/           # Unit tests
```

---

## Compilation Instructions

### Step 1: Compile the Java Files  
From the root directory, compile the source files into the `out/` directory:

```bash
javac -d out src/*.java
```

### Step 2: Create the Executable JAR  
Package the compiled files into an executable JAR file using the provided `MANIFEST.MF`:

```bash
jar cmf src/META-INF/MANIFEST.MF PixelPets.jar -C out . -C src res
```

---

## Running the Game

### Using the JAR File  
Once compiled, run the game with the following command:

```bash
java -jar PixelPets.jar
```

### Running Without JAR  
Alternatively, you can directly run the game using the main class:

```bash
java -cp out MainMenu
```

---

## Game Features

- Choose from different pet types (Dog, Cat, Bird).  
- Monitor pet stats:
  - **Health**: Keep your pet healthy by taking it to the vet.  
  - **Hunger**: Feed your pet when it gets hungry.  
  - **Social**: Play with your pet to boost its happiness.  
  - **Sleep**: Ensure your pet gets enough rest.  
  - **Mood**: Maintain overall balance to keep your pet in a good mood.  
- Simple and intuitive user interface (mouse and keyboard controls).  

---

## Requirements

- **Java Runtime Environment (JRE)**: Version 8 or higher.  
- **Java Development Kit (JDK)**: For compilation (if not using the precompiled JAR).  

---

## User Guide

### Main Menu  
- **Start New Game**: Adopt a pet and begin your journey.  
- **Load Game**: Resume a previously saved game.  
- **Instructions**: View a tutorial on how to play.  
- **Parental Controls**: Adjust settings for younger players (password protected).  

### In-Game Commands  
- Use buttons
  - Feed your pet.  
  - Play with your pet.  
  - Send your pet to sleep.  
  - Take your pet to the vet.  

### Saving and Loading  
- **Save Progress**: Save your current game state to the `saves/` directory.  
- **Load Progress**: Load previously saved games from the main menu.  

---

## Parental Controls

The game includes a password-protected parental controls screen where parents can:  
1. Set playtime limits for the player.  
2. View playtime statistics.  

**Default Password**: `CS2212A`  


---

## Credits

Developed by Group 7 for UWO, Fall 2024.  

---
