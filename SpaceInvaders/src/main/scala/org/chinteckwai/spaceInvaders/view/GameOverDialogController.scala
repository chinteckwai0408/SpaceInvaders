package org.chinteckwai.spaceInvaders.view

import org.chinteckwai.spaceInvaders.model.Player
import org.chinteckwai.spaceInvaders.MainApp
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._

@sfxml
class GameOverDialogController (
    private val scoreText : Text
) {
    var dialogStage : Stage = null

     // Set the Score text to be the score of the current player in the game
    scoreText.text = MainApp.currentPlayer.score.value.toString

    
    // function to be called when the "Continue" button is clicked
    def handleContinue(): Unit = {
        MainApp.playSelectionSound()
        dialogStage.close()
        MainApp.showMenu()
    }
}