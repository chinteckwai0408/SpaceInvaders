package org.chinteckwai.spaceInvaders.model

// Invader with the role of Lieutenant
object Lieutenant extends Invader() {
    def showInvaderType(): String = "Lieutenant"
    
    override def returnPoints(): Int = 10
}
