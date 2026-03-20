# fastRunner - Architecture Diagram

## Class Diagram

```mermaid
classDiagram
    %% Main Application Entry
    class Lwjgl3Launcher {
        +main(String[] args)
        +createApplication() Lwjgl3Application
        +getDefaultConfiguration() Lwjgl3ApplicationConfiguration
    }

    %% Core Application
    class Main {
        -int currentLevel
        -StartingScreen startingScreen
        -Level_1 level_1
        -Level_2 level_2
        -Level_3 level_3
        -Level_4 level_4
        -Test test
        +create()
        +render()
        +resize(int, int)
        +dispose()
    }

    %% Application Listener Interface
    class ApplicationListener {
        <<interface>>
        +create()
        +render()
        +resize(int, int)
        +pause()
        +resume()
        +dispose()
    }

    %% Base Level Class
    class Level_Master {
        -PerspectiveCamera cam
        -CameraInputController camController
        -ModelBatch modelBatch
        -Player player
        -Environment enviroment
        -Array~Box~ groundArray
        -Vector~Coin~ coinArray
        -Vector~PowerUp~ powerUpArray
        -CollisionDetection collisionDetection
        -EndOfLevel endOfLevel
        -int health
        -int score
        +masterRender()
        +masterInput()
        +create()
        +render()
        +getCreated() boolean
    }

    %% Level Implementations
    class Level_1 {
        -Box groundBox_1
        -Box groundBox_2
        -Box groundBox_3
        -Coin coin
        -PowerUp powerUp
        -EndOfLevel endOfLevel
        +create()
    }

    class Level_2 {
        -Box groundBox
        -Box groundBox_2
        +create()
    }

    class Level_3 {
        +create()
        +render()
    }

    class Level_4 {
        +create()
        +render()
    }

    %% Starting Screen
    class StartingScreen {
        -OrthographicCamera camera
        -SpriteBatch batch
        -BitmapFont font
        -ShapeDrawer drawer
        -Texture backgroundTexture
        -Texture level_1, level_2, level_3, level_4
        -int menuPosition
        -int currentLevel
        +create()
        +render()
        +input()
        +getCurrentLevel() int
    }

    %% Test Class
    class Test {
        -PerspectiveCamera cam
        -ModelBatch modelBatch
        -Player player
        -Array~Coin~ coinArray
        -Array~Box~ groundArray
        -CameraInputController camController
        +create()
        +render()
    }

    %% Entity Base Classes
    class Box {
        -btCollisionShape myShape
        -btCollisionObject myObject
        -Model model
        -ModelInstance myModel
        -float x, y, z
        -float width, height, depth
        +Box(float, float, float, float, float, float)
        +move(float, float, float)
        +getModel() ModelInstance
        +getObject() btCollisionObject
        +getTop() float
    }

    class Sphere {
        -btCollisionShape myShape
        -btCollisionObject myObject
        -ModelBuilder modelBuilder
        -float x, y, z
        +Sphere(float, float, float, float, float, float)
        +move(float, float, float)
        +getModel() ModelInstance
        +getObject() btCollisionObject
    }

    %% Entity Implementations
    class Player {
        -AssetManager assets
        -Model ship
        -ModelInstance shipInstance
        -float verticalSpeed
        -float verticalAcceleration
        -float movementSpeed
        -boolean powerUp
        +setPowerUp(boolean)
        +jump(float)
        +fall(float)
        +horizontalMovement(boolean, boolean, boolean, boolean, float)
        +verticalMovement(float)
        +hitTheGround(float)
    }

    class Coin {
        +Coin(float, float, float, float, float, float)
        +booleanDetectPlayer(Player, CollisionDetection) boolean
        +getCollisionObject() btCollisionObject
    }

    class PowerUp {
        +PowerUp(float, float, float, float, float, float)
        +booleanDetectPlayer(Player, CollisionDetection) boolean
        +getCollisionObject() btCollisionObject
    }

    class EndOfLevel {
        -AssetManager assets
        -Model endOfLevel
        -ModelInstance endOfLevelInstance
        +EndOfLevel(float, float, float, float, float, float)
    }

    %% Collision Detection
    class CollisionDetection {
        -btDispatcher dispatcher
        -btCollisionConfiguration collisionConfig
        +CollisionDetection()
        +checkCollision(btCollisionObject, btCollisionObject) boolean
    }

    %% Relationships
    Lwjgl3Launcher --> Main : launches
    ApplicationListener <|.. Main : implements
    ApplicationListener <|.. Level_Master : implements
    ApplicationListener <|.. StartingScreen : implements
    ApplicationListener <|.. Test : implements

    Main --> StartingScreen : uses
    Main --> Level_1 : uses
    Main --> Level_2 : uses
    Main --> Level_3 : uses
    Main --> Level_4 : uses
    Main --> Test : uses

    Level_Master <|-- Level_1 : extends
    Level_Master <|-- Level_2 : extends
    Level_Master <|-- Level_3 : extends
    Level_Master <|-- Level_4 : extends

    Level_Master --> Player : contains
    Level_Master --> CollisionDetection : uses
    Level_Master --> Box : manages array
    Level_Master --> Coin : manages array
    Level_Master --> PowerUp : manages array
    Level_Master --> EndOfLevel : contains

    Box <|-- Player : extends
    Box <|-- Coin : extends
    Box <|-- PowerUp : extends
    Box <|-- EndOfLevel : extends

    Player --> CollisionDetection : uses
    Coin --> CollisionDetection : uses
    PowerUp --> CollisionDetection : uses

    Test --> Player : contains
    Test --> Box : manages array
    Test --> Coin : manages array
```

## Component Interaction Flow

```mermaid
flowchart TD
    subgraph Launcher["Launcher (lwjgl3 module)"]
        Lwjgl3Launcher["Lwjgl3Launcher<br/>Entry Point"]
    end

    subgraph Core["Core Module"]
        Main["Main<br/>Application Controller"]
        
        subgraph Screens["Screens/Levels"]
            StartingScreen["StartingScreen<br/>Main Menu"]
            Level_Master["Level_Master<br/>Base Level Class"]
            Level_1["Level_1"]
            Level_2["Level_2"]
            Level_3["Level_3 (Empty)"]
            Level_4["Level_4 (Empty)"]
            Test["Test<br/>Debug Mode"]
        end

        subgraph Entities["Game Entities"]
            Player["Player<br/>Extends Box"]
            Box["Box<br/>Base Entity"]
            Coin["Coin<br/>Collectible"]
            PowerUp["PowerUp<br/>Power-up Item"]
            EndOfLevel["EndOfLevel<br/>Level Exit"]
            Sphere["Sphere<br/>Physics Shape"]
        end

        CollisionDetection["CollisionDetection<br/>Physics System"]
    end

    subgraph External["External Libraries"]
        libGDX["libGDX Framework<br/>Graphics, Input, Audio"]
        Bullet["Bullet Physics<br/>Collision Detection"]
    end

    Lwjgl3Launcher --> Main
    Main --> StartingScreen
    Main --> Level_1
    Main --> Level_2
    Main --> Level_3
    Main --> Level_4
    Main --> Test

    Level_1 --> Level_Master
    Level_2 --> Level_Master
    Level_3 --> Level_Master
    Level_4 --> Level_Master

    Level_Master --> Player
    Level_Master --> Box
    Level_Master --> Coin
    Level_Master --> PowerUp
    Level_Master --> EndOfLevel
    Level_Master --> CollisionDetection

    Player --> Box
    Coin --> Box
    PowerUp --> Box
    EndOfLevel --> Box

    Player --> CollisionDetection
    Coin --> CollisionDetection
    PowerUp --> CollisionDetection
    Level_Master --> CollisionDetection

    CollisionDetection --> Bullet
    Main --> libGDX
    Level_Master --> libGDX
    StartingScreen --> libGDX
    Test --> libGDX
    Box --> Bullet
```

## Data Flow Diagram

```mermaid
flowchart LR
    subgraph Input["Input Handling"]
        Keyboard["Keyboard Input<br/>WASD, Space, Arrows"]
    end

    subgraph GameLogic["Game Logic"]
        PlayerInput["Player Movement"]
        Physics["Physics Update"]
        Collision["Collision Checks"]
    end

    subgraph Rendering["Rendering"]
        ModelBatch["3D Model Batch"]
        SpriteBatch["2D Sprite Batch"]
        UI["UI/HUD Rendering"]
    end

    subgraph Output["Output"]
        Display["Display Output"]
    end

    Keyboard --> PlayerInput
    PlayerInput --> Physics
    Physics --> Collision
    Collision --> PlayerInput
    PlayerInput --> ModelBatch
    Collision --> ModelBatch
    ModelBatch --> Display
    SpriteBatch --> Display
    UI --> SpriteBatch
```

## Directory Structure

```
fastRunner/
├── core/                          # Main game logic (shared across platforms)
│   └── src/main/java/com/yourname/projectname/
│       ├── Main.java              # Application controller
│       ├── Test.java              # Debug/test mode
│       ├── CollisionDetection.java # Physics collision system
│       ├── entities/              # Game entities
│       │   ├── Box.java           # Base box-shaped entity
│       │   ├── Sphere.java        # Sphere physics helper
│       │   ├── Player.java        # Player character
│       │   ├── Coin.java          # Collectible coin
│       │   ├── PowerUp.java       # Power-up item
│       │   └── EndOfLevel.java    # Level exit marker
│       └── levels/                # Game levels
│           ├── Level_Master.java  # Base level class
│           ├── Level_1.java       # First level (complete)
│           ├── Level_2.java       # Second level
│           ├── Level_3.java       # Third level (empty)
│           ├── Level_4.java       # Fourth level (empty)
│           └── StartingScreen.java # Main menu screen
├── lwjgl3/                        # Desktop launcher (LWJGL3)
│   └── src/main/java/com/yourname/projectname/lwjgl3/
│       ├── Lwjgl3Launcher.java    # Main entry point
│       └── StartupHelper.java     # JVM startup helper
└── assets/                        # Game assets (textures, models, sounds)
```

## Key Interactions Summary

| Component | Interacts With | Purpose |
|-----------|---------------|---------|
| **Lwjgl3Launcher** | Main | Application entry point, configures window |
| **Main** | All Levels, StartingScreen, Test | Routes between screens/levels |
| **Level_Master** | Player, Entities, CollisionDetection | Core game loop, physics, rendering |
| **Level_1-4** | Level_Master | Specific level configurations |
| **Player** | Box, CollisionDetection | Player movement, physics, rendering |
| **Box** | Bullet Physics | Base collision shape for entities |
| **Coin/PowerUp** | Player, CollisionDetection | Detect player collection |
| **CollisionDetection** | Bullet Physics | Check collisions between objects |
| **StartingScreen** | libGDX UI | Menu navigation, level selection |

## Technology Stack

- **Game Framework**: libGDX
- **Physics Engine**: Bullet Physics (via gdx-bullet)
- **Build Tool**: Gradle
- **Language**: Java 8+
- **Platforms**: Desktop (LWJGL3)
