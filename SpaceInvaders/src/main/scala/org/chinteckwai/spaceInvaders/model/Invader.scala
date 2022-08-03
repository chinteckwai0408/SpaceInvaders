package org.chinteckwai.spaceInvaders.model

abstract class Invader() {
    // Return the type of Invaders (Abstract)
    def showInvaderType() : String

    // Return the points carried by the invaders (Concrete)
    def returnPoints(): Int = 0
}





