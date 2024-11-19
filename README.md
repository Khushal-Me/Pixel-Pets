# Pixel Pets

A virtual pet simulation game where you can adopt and care for different types of pets (Dog, Cat, Bird).

## Project Structure
```
.
├── README.md
├── out/           # Compiled class files
├── saves/         # Save game files
├── src/           # Source code
│   └── META-INF/  # Manifest file
└── test/          # Unit tests
```

## Compilation Instructions

From the root directory, run:

1. Compile the Java files:
```bash
javac -d out src/*.java
```

2. Create the executable JAR:
```bash
jar cmf src/META-INF/MANIFEST.MF PixelPets.jar -C out .
```

## Game Features
- Choose from different pet types
- Monitor pet stats (Health, Hunger, Social, Sleep, Mood)
- Feed, play with, and care for your pet
- Simple and intuitive UI

## Requirements
- Java Runtime Environment (JRE) 8 or higher