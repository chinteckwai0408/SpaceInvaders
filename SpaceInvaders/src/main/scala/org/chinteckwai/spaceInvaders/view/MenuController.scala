package org.chinteckwai.spaceInvaders.view
import scalafxml.core.macros.sfxml
import org.chinteckwai.spaceInvaders.MainApp

@sfxml
class MenuController() {
    
    // function to be called when the "Play Now" button is clicked
    def handlePlay(): Unit = {
        MainApp.showGameDialog()
    }

    // function to be called when the "How To Play" button is clicked
    def handleHowToPlay(): Unit = {
        MainApp.showHowToPlay()
    }

    // function to be called when the "Highscores" button is clicked
    def handleHighScores(): Unit = {
        MainApp.showHighScores()
    }

    // function to be called when the "Quit" button is clicked
    def handleQuit(): Unit = {
        MainApp.showQuitDialog()
    }
}