package org.chinteckwai.spaceInvaders.view
import scalafxml.core.macros.sfxml
import org.chinteckwai.spaceInvaders.MainApp
import scalafx.stage.Stage
import scalafx.scene.image.{Image, ImageView}
import org.chinteckwai.spaceInvaders.model.{Player, Commander, Captain, Lieutenant, Trooper}
import scalafx.Includes._
import scalafx.scene.input.{KeyEvent, KeyCode}
import scalafx.animation.TranslateTransition
import scalafx.util.Duration
import scala.collection.mutable.ListBuffer
import scalafx.animation.Timeline
import scalafx.scene.media.AudioClip
import scalafx.scene.text.Text
import scalafx.scene.input.MouseEvent

@sfxml
class GameController(
    var playerJet: ImageView, 
    val invader1: ImageView,
    val invader2: ImageView,
    val invader3: ImageView,
    val invader4: ImageView,
    val invader5: ImageView,
    val invader6: ImageView,
    val invader7: ImageView,
    val invader8: ImageView,
    val invader9: ImageView,
    val invader10: ImageView,
    val invader11: ImageView,
    val invader12: ImageView,
    val invader13: ImageView,
    val invader14: ImageView,
    val invader15: ImageView,
    val invader16: ImageView,
    val invader17: ImageView,
    val invader18: ImageView,
    val invader19: ImageView,
    val invader20: ImageView,
    val invader21: ImageView,
    val invader22: ImageView,
    val invader23: ImageView,
    val invader24: ImageView,
    val invader25: ImageView,
    val invader26: ImageView,
    val invader27: ImageView,
    val invader28: ImageView,
    var bulletNumber: Text,
    var scoreNumber: Text,
    var invaderNumber: Text
    )
     {
    
    // Classify and categorize the invaders based on their type
    val commanderInvader: ListBuffer[ImageView] = ListBuffer(invader1, invader2, invader3, invader4, invader5, invader6, invader7)
    val captainInvader: ListBuffer[ImageView] = ListBuffer(invader8, invader9, invader10, invader11, invader12, invader13, invader14)
    val lieutenantInvader: ListBuffer[ImageView] = ListBuffer(invader15, invader16, invader17, invader18, invader19, invader20, invader21)
    val trooperInvader: ListBuffer[ImageView] = ListBuffer(invader22, invader23, invader24, invader25, invader26, invader27, invader28)
    
    // Set the initial bullet number to be 41 (because it will decrease by 1 when the game is initialize, so when game starts, the bullet number will reduce to become 40)
    var bulletLeft: Int = 41

    // Set the initial score to 0
    var playerScore: Int = 0

     // The ListBuffer of all the Invaders in the game
    val allInvaders: ListBuffer[ImageView] = commanderInvader ++ captainInvader ++ lieutenantInvader ++ trooperInvader

    // An Integer variable to indicate the number of invaders left in the game is created
    var invadersLeft: Int = allInvaders.length

    // An Integer variable indicating the goal of the achievement
    var achievementGoal: Int = 100

    // Data model (the data of the current player obtained from MainApp)
    var _player: Player = MainApp.currentPlayer
  
    // Show the jet chosen by the player in the game screen
    def displayJet(): Unit = {
         _player.jetChosen.value match {
        case 1 => playerJet.image = new Image(getClass.getResourceAsStream("/images/jet1.png"))
        case 2 => playerJet.image = new Image(getClass.getResourceAsStream("/images/jet2.png"))
        case 3 => playerJet.image = new Image(getClass.getResourceAsStream("/images/jet3.png"))
        }
    }

    // Display the game components such as bullets left, score and number of invaders
    def loadingGameComponents(): Unit = {
        scoreNumber.text = playerScore.toString()
        bulletNumber.text = bulletLeft.toString()
        invaderNumber.text = invadersLeft.toString()
    }

    // Movements for the invaders in the Game
    def invadersMovement(): Unit = {
        for (invader <- commanderInvader) {
            val movement = new TranslateTransition(new Duration(700), invader)
            movement.fromX = -75
            movement.toX = 75
            movement.autoReverse = true
            movement.cycleCount = Timeline.Indefinite
            movement.play()
        }

        for (invader <-lieutenantInvader) {
            val movement = new TranslateTransition(new Duration(800), invader)
            movement.fromX = -75
            movement.toX = 75
            movement.autoReverse = true
            movement.cycleCount = Timeline.Indefinite
            movement.play()
        }

        for (invader <- captainInvader ) {
            val movement = new TranslateTransition(new Duration(900), invader)
            movement.fromX = 75
            movement.toX = -75
            movement.autoReverse = true
            movement.cycleCount = Timeline.Indefinite
            movement.play()
        }

         for (invader <- trooperInvader) {
            val movement = new TranslateTransition(new Duration(1100), invader)
            movement.fromX = 75
            movement.toX = -75
            movement.autoReverse = true
            movement.cycleCount = Timeline.Indefinite
            movement.play()
        }
    }

    // Actions when the invader is shot and destroyed
    def activateInvaders(): Unit = {

        for (x <- allInvaders) {
            x.onMouseClicked = _ => {
                // Hide and disable the invaders
                x.visible = false
                x.disable = true

                // update the number of invaders left
                invadersLeft -= 1
                invaderNumber.text = invadersLeft.toString()

                playShootSound()
                if (commanderInvader.contains(x)) {
                    addScore(Commander.returnPoints()) // when commanderInvader is destroyed
                } 
                if (captainInvader.contains(x)) {
                    addScore(Captain.returnPoints()) // when captainInvader is destroyed
                } 
                if (lieutenantInvader.contains(x)) {
                    addScore(Lieutenant.returnPoints()) // when lieutenantInvader is destroyed
                } 
                if (trooperInvader.contains(x)) {
                    addScore(Trooper.returnPoints()) // when trooperInvader is destroyed
                } 

                // Check whether all the invaders have been destroyed
                allInvadersDestroyed(invadersLeft)
             }
        }
    }

    // To check whether all the invaders have been destroyed
    def allInvadersDestroyed(invadersLeft: Int): Unit = {

        // If no invaders left
        if (invadersLeft <= 0) {
            MainApp.hasPlayerWin = true
            // The player score will be added by 10 for every remaining bullets left
            playerScore += (bulletLeft * 10)

            // Save the score of the player
            MainApp.currentPlayer.score.value = playerScore

            // Save the player record into the Player Table of database
            MainApp.currentPlayer.save()
            Player.playerData += MainApp.currentPlayer
            
            MainApp.stopBackgroundMusic()
            playVictorySound()

            // Show the Game Victory Dialog Stage
            MainApp.showGameVictory()
        }
    }
    
    // To update the number of bullets of the player
    def updateBullets(): Unit = {
        bulletLeft -= 1
        bulletNumber.text = bulletLeft.toString()
        checkOutOfBullet(bulletLeft)
    }

    // To check whether the player is out of bullets (Remaining bullets = 0)
    def checkOutOfBullet(bulletLeft: Int): Unit = {
        if (bulletLeft <= 0) {

            if (!MainApp.hasPlayerWin) {
                // Save the score of the player
                MainApp.currentPlayer.score.value = playerScore

                // Save the player record into the table of database
                MainApp.currentPlayer.save()
                Player.playerData += MainApp.currentPlayer
                
                MainApp.stopBackgroundMusic()
                playLoseSound()
                
                // Show the Game Over Dialog Stage
                MainApp.showGameOver()
            }
        }
    }
    
    // Function called to initialize the components of the game
    def initializeGame(): Unit = {
        displayJet()
        loadingGameComponents()
        invadersMovement()
        activateInvaders()
        updateBullets()
    }

    // For adding the score of the player
    def addScore(enemyPoints: Int): Unit = {
        playerScore += enemyPoints
        scoreNumber.text = playerScore.toString()
        playAchievementSound()
    }

    // function to play the Shooting Sound Effect
    def playShootSound(): Unit = {
        val sound = new AudioClip(getClass.getResource("/sound/shoot.mp3").toString())
        sound.volume = 0.3
        sound.play()
    }
    
    // function to play the Achievement Sound Effect
    def playAchievementSound(): Unit = {
        // The sound effect will be played for every 100 points gained by the player
        if (playerScore >= achievementGoal) {
            val scoreUnlocked = new AudioClip(getClass.getResource("/sound/score100.mp3").toString())
            scoreUnlocked.volume = 0.3
            scoreUnlocked.play()
            achievementGoal += 100      // update the achievement goal once it has been achieved
        }
    }

    // function to play the Victory Sound Effect
    def playVictorySound(): Unit = {
        val victorySound = new AudioClip(getClass.getResource("/sound/victory.mp3").toString())
        victorySound.volume = 0.3
        victorySound.play()
    }

    // function to play the Lost Sound Effect
    def playLoseSound(): Unit = {
        val lostSound = new AudioClip(getClass.getResource("/sound/gameOver.mp3").toString())
        lostSound.volume = 0.3
        lostSound.play()
    }

    // actions when the keyboard button is pressed
    def handleKeyPressed(e: KeyEvent): Unit = {
        // If left-arrow button is clicked
        if (e.code == KeyCode.Left)
            moveLeft()

        // If right-arrow button is clicked
        if (e.code == KeyCode.Right) 
           moveRight()
    }

    // Move the jet to the left
    def moveLeft(): Unit = {
        playerJet.layoutX = playerJet.layoutX() - 10
    }

    // Move the jet to the right
    def moveRight(): Unit = {
        playerJet.layoutX = playerJet.layoutX() + 10
    }
}
