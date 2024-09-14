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
- **[Components](/engine/src/components)**: Represents game entities like [Board](/engine/src/components/board) and [Card](/engine/src/components/card), crucial for defining the game's structure and dynamics.
- **[Engine](/engine/src/engine)**: Manages game flow and logic ([`Engine`](/engine/src/engine/engine/Engine.java) and [`EngineImpl`](/engine/src/engine/engine/EngineImpl.java)).
- **[Loader](/engine/src/engine/data/loader)**: Loads game data from XML configurations.
- **[Validators](/engine/src/engine/data/validators)**: Validates game configurations and player inputs.

### [2. Admin Module](/adminclient)
The Admin Module provides the tools necessary for administrators to configure and oversee game sessions.
- **[AdminClientMain](/adminclient/src/AdminClientMain.java)**: Entry point for the admin client application, allowing the admin to interact with the game server.
- **[FileUpload](/adminclient/src/FileUpload.java)**: Handles the upload of game configurations, enabling the admin to set up games with customized rules and parameters
- **[WatchLiveGame](/adminclient/src/WatchLiveGame.java)**: Allows the admin to watch live games as an observer.

### [3. Player Module](/playerclient)
The Player Module empowers players to engage with the game, from registration to active participation in game sessions. It manages player interactions and game state updates during gameplay.
- **[PlayerClientMain](/playerclient/src/PlayerClientMain.java)**: EEntry point for the player client application, providing access to game functionalities for players.
- **[RegisterToGame](/playerclient/src/RegisterToGame.java)**: Manages player registration for games.
- **[PlayTurn](/playerclient/src/PlayTurn.java)**: Manages the player's turn during the game, processing actions such as providing clues or guessing words.

### [4. Common Module](/common)
The Common Module contains shared utilities and components used across the application, ensuring consistency and reusability in how game information is presented and client-server communication is handled.
- **[UI](/common/src/ui)**: Includes ['BoardPrinter'](/common/src/ui/BoardPrinter.java) and ['CardToString'](/common/src/ui/BoardPrinter.java) for displaying game information, offering a standardized way to visualize the game state.
- **[Utils](/common/src/utils)**: Provides common utility functions, simplifying tasks like data manipulation and error handling.
- **[CallConfig](/common/src/callconfig)**:  Manages HTTP communication between clients and the server using [OkHttp](https://github.com/square/okhttp), ensuring seamless data exchange and interaction.

### [5. Webapp Module](/webbapp)
Handles web-related functionalities and serves as the interface between clients and the engine core.
- **FileUploadServlet**: Manages file upload requests.
- **LiveGamesServlet**: Provides live game status.
- **PlayTurnServlet**: Manages player turns during the game.
