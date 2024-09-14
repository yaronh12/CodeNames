# CodeNames Project

## Overview
The **CodeNames** project is a client-server-based multiplayer game implemented using Java and Tomcat. It allows multiple users to participate in various game sessions with different configurations and roles, including players and administrators.


## Technologies Used
- Apache Tomcat (v10.0.11)
- Java (v8)
- XML (for game configuration)

## System Requirements
- JDK 8
- Apache Tomcat 10
- Maven/Gradle (for building the project)

## Architecture

### Server

The server, built on Tomcat, follows a RESTful architecture to host the game engine. It manages game sessions, player registrations, and overall game states through a set of RESTful endpoints. The core game logic is contained within the game engine, while servlets act as intermediaries that handle communication between the server and clients by processing requests and responses.

### Clients
- **Admin Client**: A console application to upload game configurations, view game statuses, and join active games as an observer.
- **Player Client**: A console application for users to register, view available games, join, and participate in game sessions.

## Game Flow

### Admin View
1. **Upload Game Configuration**:
   - Upload XML files containing game configurations, including board size, dictionary, and team setups.
2. **View Game Status**:
   - Monitor all games with details like board size, dictionary file, team configurations, and current active game states.
3. **Join Active Game as Observer**:
   - Observe the current game status, including board state and scores.

### Player View
1. **Register and Login**:
   - Register with a unique username and log in to access games.
2. **View Available Games**:
   - Browse and select games to join based on preferences..
3. **Join a Game**:
   - Choose a team and role (Definer or Guesser) in the selected game.
4. **Participate in the Game**:
   - Definers provide clues, and Guessers identify words to progress.
5. **Game Completion**:
   - The game concludes when a winning condition is met, such as one team uncovering all their words or all but one team being eliminated. Players receive a summary of the game.

## Modules

### [1. Engine Module](/engine)
The Engine Module is the backbone of the CodeNames game, encompassing the core logic and rules that drive gameplay. It ensures the correct flow and state management of the game while validating inputs and configurations.
- **[Components](/engine/src/components)**: Represents game entities like Board and Card, crucial for defining the game's structure and dynamics.
- **Engine**: Manages game flow and logic (`Engine` and `EngineImpl`).
- **Loader**: Loads game data from XML configurations.
- **Validators**: Validates game configurations and player inputs.

### [2. Admin Module](/playerclient)
The Admin Module provides the tools necessary for administrators to configure and oversee game sessions.
- **AdminClientMain**: Entry point for the admin client application.
- **FileUpload**: Handles the upload of game configurations.
- **WatchLiveGame**: Allows the admin to watch live games as an observer.

### [3. Player Module](/adminclient)
Provides functionalities for players to register, join, and participate in games.
- **PlayerClientMain**: Entry point for the player client application.
- **RegisterToGame**: Manages player registration for games.
- **PlayTurn**: Manages the player's turn during the game.

### [4. Common Module](/common)
Contains shared utilities and UI components.
- **UI**: Includes `BoardPrinter` and `CardToString` for displaying game information.
- **Utils**: Provides common utility functions.
- **CallConfig**: Manages HTTP communication between clients and the server using OkHttp.

### [5. Webapp Module](/webbapp)
Handles web-related functionalities and serves as the interface between clients and the engine core.
- **FileUploadServlet**: Manages file upload requests.
- **LiveGamesServlet**: Provides live game status.
- **PlayTurnServlet**: Manages player turns during the game.
