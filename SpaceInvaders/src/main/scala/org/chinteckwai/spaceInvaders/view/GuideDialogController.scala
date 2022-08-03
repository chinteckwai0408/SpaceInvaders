package org.chinteckwai.spaceInvaders.view
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import org.chinteckwai.spaceInvaders.MainApp

@sfxml
class GuideDialogController() {
    var dialogStage: Stage = null // window reference

    // function to be called when the "Continue" button is clicked
    def handleContinue(): Unit = {
        MainApp.playSelectionSound()
        dialogStage.close()
    }

}