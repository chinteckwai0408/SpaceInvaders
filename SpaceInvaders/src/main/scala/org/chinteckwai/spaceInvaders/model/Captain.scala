package org.chinteckwai.spaceInvaders.model

// Invader with the role of Captain
object Captain extends Invader() {
    def showInvaderType(): String = "Captain"

    override def returnPoints(): Int = 15
}