package org.chinteckwai.spaceInvaders.model
import scalafx.beans.property.{StringProperty, ObjectProperty}
import org.chinteckwai.spaceInvaders.util.Database
import scalikejdbc._
import scala.util.{Try, Success, Failure}
import scalafx.collections.ObservableBuffer

class Player(val nameS: String, val jetChosenI: Int, var scoreI: Int) extends Database {
    def this()    = this(null, 0, 0)
    val name      = new StringProperty(nameS)
    val jetChosen = ObjectProperty[Int](jetChosenI)
    var score     = ObjectProperty[Int](scoreI)

    // Inserting the player record into the Player Table
    def save(): Try[Int] = {
        Try(DB autoCommit { implicit session => 
        sql"""
            insert into player (name, jetChosen, score) values
            (${name.value}, ${jetChosen.value}, ${score.value})
            """.update.apply()
            }) 
    }

    // Deleting all the player records from the Player Table
    // This delete function is not used unless the developer wants to clear the player records from the database
    def delete(): Try[Int] = {
        Try (DB autoCommit { implicit session =>
        sql"""
        delete from player
        """.update.apply()})
    }
}

object Player extends Database {
    val playerData = new ObservableBuffer[Player]()

    def apply (
        nameS: String,
        jetChosenI : Int,
        scoreI : Int
    ) : Player = {
        new Player(nameS, jetChosenI, scoreI)
    }

    // Creating Player Table with the necessary fields in the Database
    def initializeTable() = {
        DB autoCommit { implicit session =>
        sql"""
            create table player (
                id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                name varchar(64),
                jetChosen int,
                score int
            )
            """.execute.apply()
            }
    }

    // Retreive all the player records from the Player Table into a list
    def allPlayers: List[Player] = {
        DB readOnly { implicit session => 
        sql"select * from player".map(rs => Player(rs.string("name"),
            rs.int("jetChosen"),
            rs.int("score"))).list.apply()
            }
    }

}
