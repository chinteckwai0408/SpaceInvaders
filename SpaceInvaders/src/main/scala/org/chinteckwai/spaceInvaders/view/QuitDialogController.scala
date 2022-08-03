package org.chinteckwai.spaceInvaders.view
import scalafxml.core.macros.sfxml
import org.chinteckwai.spaceInvaders.MainApp
import scalafx.stage.Stage

@sfxml
class QuitDialogController() {

    var dialogStage: Stage = null // window reference

    // function to be called when "YES" button is clicked
    def handleYes(): Unit = {
        MainApp.playSelectionSound()
        dialogStage.close()

        // Check whether the player is in the gameplay (Game Window)
        if (MainApp.hasGameStarted) {
            // The player will be returned back to the Menu Window if the player is in the Game Window
            MainApp.showMenu()
        } else {
            // The game will be closed if the player is not in gameplay (Game Window)
            MainApp.closeApp()
        }
    }

    // function to be called when the "No" button is clicked
    def handleNo(): Unit = {
        MainApp.playSelectionSound()
        dialogStage.close()
    }

}