package org.chinteckwai.spaceInvaders.model

// Invader with the role of Commander
object Commander extends Invader() {
    def showInvaderType(): String = "Commander"
    
    override def returnPoints(): Int = 20
}