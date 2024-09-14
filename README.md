# CodeNames Project

The CodeNames project is a client-server based game implemented using Java and Tomcat. It allows multiple users to participate in different game sessions with varying configurations and roles.

## Technologies Used
- **Apache Tomcat:** Version 10.0.11
- **Java:** Version 8
- **XML:** For game configuration

## Architecture

### Server
The server is built on Tomcat and hosts the game engine, exposing various endpoints for client interactions. It manages game sessions, player registrations, and game states.

### Clients
- **Admin Client:** A console application for uploading game configurations, viewing game statuses, and joining active games as an observer.
- **Player Client:** A console application for users to register, view available games, join games, and participate in game sessions.

## Game Flow

### Admin View
1. **Upload Game Configuration**
   - Admin uploads XML files containing game configurations.
   - Each configuration is added to the server, increasing the variety of available games.
2. **View Game Status**
   - Admin can view a list of all games, including their status (Pending or Active).
   - Details include board size, dictionary file, number of unique words, and team configurations.
3. **Join Active Game as Observer**
   - Admin can join any active game session as an observer.
   - View the current game status, including the board state, teams, scores, and the current turn.

### Player View
1. **Register and Login**
   - Player registers with a unique username. If the username is taken, they are prompted to choose another.
2. **View Available Games**
   - Players can see all games in the system, including required teams, roles (Definers and Guessers), and registration status.
3. **Join a Game**
   - Players select a game to join, choose a team, and select a role.
   - Players wait in the game room until the game status changes to Active.
4. **Participate in the Game**
   - Definers provide clues, and Guessers guess words based on the clues.
   - The game continues until one team successfully guesses all their words or a team reveals a black word, leading to immediate loss.
5. **Game Completion**
   - The game ends when one team wins or all but one team is eliminated.
   - Players return to the main menu to join or start another game.

## Project Modules

### 1. Engine Module
The core of the CodeNames game logic. It handles game state management, game configurations, and enforces game rules.

**Key Components:**
- **Components:** Classes like `Board` and `Card` representing the game board and cards.
- **Engine:** Main game engine managing the flow and logic.
- **Game:** Manages individual game states.
- **Loader:** Handles loading game data from configuration files.
- **Validators:** Validates game configurations and player inputs.

### 2. Admin Module
Handles functionalities available to the administrator, such as uploading game configurations, viewing game statuses, and monitoring active games.

**Key Components:**
- `AdminClientMain`: Entry point for the Admin client application.
- `AdminMenus`: Manages menu options for the admin.
- `DisplayGamesDetails`: Displays game details for the admin.
- `FileUpload`: Handles game configuration file uploads.
- `WatchLiveGame`: Allows the admin to observe live games.

### 3. Player Module
Manages functionalities available to the players, including registration, joining games, and participating in game sessions.

**Key Components:**
- `PlayerClientMain`: Entry point for the Player client application.
- `PlayerMenus`: Manages menu options for players.
- `RegisterToGame`: Handles player registration for games.
- `PendingGamesDisplay`: Shows the list of available games for players to join.
- `PlayTurn`: Manages the player's turn during the game.
- `GetGamesDetails`, `LiveGameStatus`: Retrieve game details and live status.

### 4. Common Module
Contains shared utilities and UI components used across both admin and player modules.

**Key Components:**
- **UI:** Classes like `BoardPrinter` and `CardToString` for displaying game information.
- **Utils:** Common utility functions used across different modules.
- **CallConfig:** Manages HTTP communication between clients and the server.

### 5. Webapp Module
Handles web-related functionalities and serves as the interface between the clients and the game engine core. Contains servlets that process client requests.

**Key Components:**
- `FileUploadServlet`: Manages file upload requests from the admin.
- `LiveGamesServlet`: Provides live game status to the admin.
- `GameRegistryServlet`: Handles game registration requests from players.
- `GameStatusServlet`: Provides current game status to players.
- `PendingGamesServlet`: Lists pending games for players.
- `PlayTurnServlet`: Manages player turns during the game.
