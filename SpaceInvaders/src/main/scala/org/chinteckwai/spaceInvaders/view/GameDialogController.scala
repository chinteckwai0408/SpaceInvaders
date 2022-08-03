package org.chinteckwai.spaceInvaders.view
import scalafxml.core.macros.sfxml
import org.chinteckwai.spaceInvaders.MainApp
import scalafx.stage.Stage
import scalafx.scene.image.ImageView
import scalafx.scene.input.MouseEvent
import org.chinteckwai.spaceInvaders.model.Player
import scalafx.scene.control.TextField
import scalafx.scene.control.{TextField, Alert}
import scalafx.Includes._

@sfxml
class GameDialogController(
    private val nameField: TextField,
    private val jetOption1 : ImageView,
    private val jetOption2 : ImageView,
    private val jetOption3 : ImageView
) {
    var dialogStage: Stage = null       // window reference
    var _player = new Player("", 0, 0)  // data model
    var okClicked: Boolean = false      // user choice

    // An Integer variable storing the player choice of jet is created (Default value = 0)
    var _jetChosen: Int = 0 
    

    // function to be called when the "OK" button is clicked
    def handleOk(): Unit = {
        MainApp.playSelectionSound()
        
        if (isInputValid()) {
            _player.name.value = nameField.text()
            _player.jetChosen.value = _jetChosen

            // Save the _player data to the currentPlayer in MainApp
            MainApp.currentPlayer  = _player
            okClicked = true
            dialogStage.close()
        }
    }

    // function to be called when the "Cancel" button is clicked
    def handleCancel(): Unit = {
        MainApp.playSelectionSound()
        dialogStage.close()
    }

    // action when jet 1 is selected by the player
    def handleJet1(event: MouseEvent): Unit = {
        MainApp.playSelectionSound()

        // To indicate the jet 1 is chosen, display other jets with lower opacity
        jetOption1.opacity = 1
        jetOption2.opacity = 0.3
        jetOption3.opacity = 0.3
        _jetChosen = 1
    }

    // action when jet 2 is selected by the player
    def handleJet2(event: MouseEvent): Unit = {
        MainApp.playSelectionSound()
        jetOption2.opacity = 1
        jetOption1.opacity = 0.3
        jetOption3.opacity = 0.3
        _jetChosen = 2
    }

    // action when jet 3 is selected by the player
    def handleJet3(event: MouseEvent): Unit = {
        MainApp.playSelectionSound()
        jetOption3.opacity = 1
        jetOption1.opacity = 0.3
        jetOption2.opacity = 0.3
        _jetChosen = 3
    }

    // To check whether the string is empty
    def nullChecking(x : String) = x == null || x.length == 0

    // To determine whether the player input is valid
    def isInputValid(): Boolean = {
        var errorMessage = ""

        // To check whether the name text field is empty
        if (nullChecking(nameField.text.value))
            errorMessage += "The name is not valid!\n"
        
        // To check whether the player has not chosen a jet
        if (_jetChosen == 0)
            errorMessage += "You have not choose a jet yet!\n"

        // To check whether there are any error messages
        if (errorMessage.length() == 0) {
            return true
        } else {
            // Show the error message
            val alert = new Alert(Alert.AlertType.Error) {
                initOwner(dialogStage)
                title = "Invalid Input"
                headerText = "Please enter and choose the correct details"
                contentText = errorMessage
            }.showAndWait()

            return false
        }
    }

}