package org.chinteckwai.spaceInvaders.model

// Invader with the role of Trooper
object Trooper extends Invader() {
    def showInvaderType(): String = "Trooper"
    
    override def returnPoints(): Int = 5
}