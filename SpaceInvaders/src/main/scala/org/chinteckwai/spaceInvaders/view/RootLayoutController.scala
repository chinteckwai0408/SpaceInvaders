package org.chinteckwai.spaceInvaders.view
import scalafxml.core.macros.sfxml
import org.chinteckwai.spaceInvaders.MainApp
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.image.{Image, ImageView}

@sfxml
class RootLayoutController() {

    // function to start the game
    def handleStart(): Unit = {
        // Check whether the game has already started
        if (!MainApp.hasGameStarted) {
            // Show the Game Intro Dialog Stage if the game has not yet started
            MainApp.showGameDialog()
        }
    }
    
    // function to quit the game
    def handleQuit(): Unit = {
        MainApp.showQuitDialog()
    }

    // function to display the Highscores Window
    def handleHighScores(): Unit = {
        MainApp.showHighScores()
    }

    // function to display the Guide (How To Play) Dialog Stage
    def handleHowToPlay(): Unit = {
        MainApp.showHowToPlay()
    }

    // function to display the About Game Dialog Stage
    def handleAbout(): Unit = {
        MainApp.playSelectionSound()

        // show the Information Alert Box
        new Alert(AlertType.Information) {
            initOwner(MainApp.stage)
            title = "About"
            headerText = "The Legendary Space Invaders Game"
            contentText = "An alien shooting video game created by\nChin Teck Wai and inspired by the Space\nInvaders Game"
            // Custom icon for the graphic of alert box
            graphic = new ImageView(new Image(getClass.getResourceAsStream("/images/icon.jpg"), 40,40, true, true))
        }.showAndWait()

    }

    // function to mute the background music
    def handleMute(): Unit = {
        MainApp.stopBackgroundMusic()
    }

    // function to play the background music
    def handlePlayMusic(): Unit = {
        // To avoid duplicate of music played at the same time
        MainApp.stopBackgroundMusic()
        MainApp.playBackgroundMusic()
    }

}
