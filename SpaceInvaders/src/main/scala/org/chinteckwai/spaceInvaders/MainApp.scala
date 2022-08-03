package org.chinteckwai.spaceInvaders
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Scene, Cursor, ImageCursor}
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.scene.media.{Media, AudioClip}
import org.chinteckwai.{spaceInvaders => cma}
import scalafx.stage.{Stage, StageStyle, Modality}
import org.chinteckwai.spaceInvaders.view.{QuitDialogController, GameDialogController, GameController, GameOverDialogController, GameVictoryDialogController, GuideDialogController}
import scalafx.scene.image.{Image, ImageView}
import scalafx.event.ActionEvent
import scalafx.application.Platform
import org.chinteckwai.spaceInvaders.model.Player
import org.chinteckwai.spaceInvaders.util.Database


object MainApp extends JFXApp {

    // Setup and Initialize the Database
    Database.setupDB()

    // Initialize the player collection from DB
    Player.playerData ++= Player.allPlayers

    // To transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResourceAsStream("view/RootLayout.fxml")

    // Initialize the loader object.
    val loader = new FXMLLoader(null, NoDependencyResolver)

    // Load root layout from fxml file
    loader.load(rootResource)

    // Retrieve the root component BorderPane from FXML
    val roots = loader.getRoot[jfxs.layout.BorderPane]

    // Instance of AudioClip for the Background Music of Game
    val backgroundMusic = new AudioClip(getClass.getResource("/sound/backgroundmusic.mp3").toString())
   
    // Create and Initialize an instance of Player
    var currentPlayer: Player = new Player("",0,0)

    // A variable to keep track of whether the game has started
    var hasGameStarted: Boolean = false

    // A variable to keep track of whether the player wins the game
    var hasPlayerWin: Boolean = false

    // Initialize stage
    stage = new PrimaryStage {
        title = "The Legendary Space Invaders"
        scene = new Scene {
            root = roots

            // CSS Stylesheet applied for the scene
            stylesheets += getClass.getResource("view/Theme.css").toString()
        }

        // Set the window to be not resizable
        resizable = false
        icons += new Image(getClass.getResourceAsStream("/images/icon.jpg"))
    }

    // function to play the background music
    def playBackgroundMusic(): Unit = {
        backgroundMusic.volume = 0.25
        backgroundMusic.play()
    }

    // function to stop the background music
    def stopBackgroundMusic(): Unit = {
        backgroundMusic.stop()
    }

    // function to play the selection sound effect
    def playSelectionSound(): Unit = {
        val selectSound = new AudioClip(getClass.getResource("/sound/click.mp3").toString())
        selectSound.volume = 0.3
        selectSound.play()
    }

    // function to display the menu window
    def showMenu(): Unit = {
        val resource = getClass.getResourceAsStream("view/MenuPage.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        this.roots.setCenter(roots)
        stage.scene().cursor = Cursor.Default
        
        // Reset Background Music
        stopBackgroundMusic()
        playBackgroundMusic()

        // Indicate the game has not yet started
        hasGameStarted = false
    }

     // function to show Game Intro Dialog Stage
    def showGameDialog(): Unit = {
        playSelectionSound()

        val resource = getClass.getResourceAsStream("view/GameIntroDialog.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots2 = loader.getRoot[jfxs.Parent]
        val gameIntroControl = loader.getController[GameDialogController#Controller]

        val gameIntroDialog = new Stage() {
            initModality(Modality.ApplicationModal)
            initOwner(stage)
            title = "Start Game"
            scene = new Scene {
                root = roots2
                stylesheets += getClass.getResource("view/Theme.css").toString()
            }
            resizable = false
            icons += new Image(getClass.getResourceAsStream("/images/icon.jpg"))
        }

    gameIntroControl.dialogStage = gameIntroDialog
    gameIntroDialog.showAndWait()

    // Start the game if the input is valid and the "OK" button is clicked
    if (gameIntroControl.okClicked)
        startGame()
    
    }

    // function to start the game (display the game screen)
    def startGame(): Unit = {
        val resource = getClass.getResourceAsStream("view/GamePage.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        this.roots.setCenter(roots)
        val gameplayControl = loader.getController[GameController#Controller]
        
        // Initialize and Setup Game Components
        gameplayControl.initializeGame()

        // Image for the Custom Game Cursor
        val shootCursor = new Image(getClass.getResourceAsStream("/images/scopeCursor.png"))

        // Change the cursor to the custom game cursor
        stage.scene().cursor = new ImageCursor(shootCursor, shootCursor.width()/2, shootCursor.height()/2)

        // Indicate that the game has started
        hasGameStarted = true

        hasPlayerWin = false
    }

    // function to show the Game Over Dialog Stage
    def showGameOver(): Unit = {
        val resource = getClass.getResourceAsStream("view/GameOverDialog.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots2 = loader.getRoot[jfxs.Parent]
        val gameOverControl = loader.getController[GameOverDialogController#Controller]

        val gameOverDialog = new Stage() {
            initModality(Modality.ApplicationModal)
            initOwner(stage)

            // Define the stage style with no decorations
            initStyle(StageStyle.Undecorated)
            title = "Game Over"
            scene = new Scene {
                root = roots2
                stylesheets += getClass.getResource("view/Theme.css").toString()
            }

            resizable = false
            icons += new Image(getClass.getResourceAsStream("/images/icon.jpg"))
        }

        gameOverControl.dialogStage = gameOverDialog

        // Shows the stage and waits for it to be closed before returning to the primary stage
        gameOverDialog.showAndWait()
    }

    // function to show the Game Victory Dialog Stage
    def showGameVictory(): Unit = {
        val resource = getClass.getResourceAsStream("view/GameVictoryDialog.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots2 = loader.getRoot[jfxs.Parent]
        val gameVictoryControl = loader.getController[GameVictoryDialogController#Controller]

        val gameWinDialog = new Stage() {
            initModality(Modality.ApplicationModal)
            initOwner(stage)
            initStyle(StageStyle.Undecorated)
            title = "You Win"
            scene = new Scene {
                root = roots2
                stylesheets += getClass.getResource("view/Theme.css").toString()
            }

            resizable = false
            icons += new Image(getClass.getResourceAsStream("/images/icon.jpg"))
        }
        
        gameVictoryControl.dialogStage = gameWinDialog
        gameVictoryControl.gameplayRating()
        gameWinDialog.showAndWait()
    }

    // function to show the Guide (How to Play) Dialog Stage
    def showHowToPlay(): Unit = {
        playSelectionSound()

        val resource = getClass.getResourceAsStream("view/GuideDialog.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots2 = loader.getRoot[jfxs.Parent]
        val guideDialogControl = loader.getController[GuideDialogController#Controller]

        val guideDialog = new Stage() {
            initModality(Modality.ApplicationModal)
            initOwner(stage)
            title = "How to Play"
            scene = new Scene {
                root = roots2
                stylesheets += getClass.getResource("view/Theme.css").toString()
            }
            resizable = false
            icons += new Image(getClass.getResourceAsStream("/images/icon.jpg"))
        }

        guideDialogControl.dialogStage = guideDialog
        guideDialog.showAndWait()
    }

    // function to show the Highscores Window
    def showHighScores(): Unit = {
        playSelectionSound()

        val resource = getClass.getResourceAsStream("view/HighScoresPage.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        this.roots.setCenter(roots)
        stage.scene().cursor = Cursor.Default
    }

    // function to show the Quit Dialog Stage
    def showQuitDialog(): Unit = {
        playSelectionSound()

        val resource = getClass.getResourceAsStream("view/QuitDialog.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots2 = loader.getRoot[jfxs.Parent]
        val quitDialogControl = loader.getController[QuitDialogController#Controller]

        val quitDialog = new Stage() {
            initModality(Modality.ApplicationModal)
            initOwner(stage)
            title = "Quit the Game"
            scene = new Scene {
                root = roots2
                stylesheets += getClass.getResource("view/Theme.css").toString()
            }

            resizable = false
            icons += new Image(getClass.getResourceAsStream("/images/icon.jpg"))
        }

        quitDialogControl.dialogStage = quitDialog
        quitDialog.showAndWait()
    }

    // function to close the game
    def closeApp(): Unit = {
       Platform.exit()
    }

    // Show the Menu Window first when the app is run and launched
    showMenu()
}

