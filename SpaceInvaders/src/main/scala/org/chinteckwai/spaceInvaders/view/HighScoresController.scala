package org.chinteckwai.spaceInvaders.view
import scalafxml.core.macros.sfxml
import org.chinteckwai.spaceInvaders.MainApp
import scalafx.scene.control.{TableView, TableColumn}
import org.chinteckwai.spaceInvaders.model.Player

@sfxml
class HighScoresController(
    private val playerTable : TableView[Player],
    private val nameColumn : TableColumn[Player, String],
    private val scoreColumn : TableColumn[Player, Int]
) {
    // Initialize Table View display contents model
    // Only includes the Top 5 Players with the highest score (by sorting and slicing the ObservableBuffer)
    playerTable.items = (Player.playerData.sortWith(_.score.value > _.score.value)).slice(0,5)

    // Initialize columns's cell values
    nameColumn.cellValueFactory = {_.value.name}
    scoreColumn.cellValueFactory = {_.value.score}

    // function to be called when the "Back" button is clicked
    def handleBack(): Unit = {
        MainApp.playSelectionSound()
        MainApp.showMenu()
    }

}