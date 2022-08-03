package org.chinteckwai.spaceInvaders.view

import org.chinteckwai.spaceInvaders.model.Player
import org.chinteckwai.spaceInvaders.MainApp
import scalafx.scene.text.Text
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._

@sfxml
class GameVictoryDialogController (
    private val scoreText : Text,
    private var oneStar: ImageView,
    private var twoStar: ImageView,
    private var threeStar: ImageView
) {
    var dialogStage : Stage = null

    // A variable for storing the score of the current player in the game
    val playerScore: Int = MainApp.currentPlayer.score.value

    // Display the score of the player in the string format
    scoreText.text = playerScore.toString

    // function to be called when the "Continue" button is clicked
    def handleContinue(): Unit = {
        MainApp.playSelectionSound()
        dialogStage.close()
        MainApp.showMenu()
    }

    // function to display the number of stars (rating of the player)
    def gameplayRating(): Unit = {
        if (playerScore < 440) {
            threeStar.visible = false

            if (playerScore < 330) {
                twoStar.visible = false

                if (playerScore < 220) {
                    oneStar.visible = false
                }
            }
        }
   
    }
}
    
