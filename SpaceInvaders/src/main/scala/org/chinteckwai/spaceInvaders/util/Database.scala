package org.chinteckwai.spaceInvaders.util
import scalikejdbc._ // library to connect to database
import org.chinteckwai.spaceInvaders.model.Player

trait Database {
    // Initialize JDBC driver & connection pool
    val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

    val dbURL = "jdbc:derby:myDB;create=true;";

    Class.forName(derbyDriverClassname)
  
    ConnectionPool.singleton(dbURL, "me", "mine")

    // ad-hoc session provider on the REPL
    implicit val session = AutoSession
}

object Database extends Database {
    // Setup the DB Table if the database has not yet been initialize
    def setupDB(): Unit = {
        if (!hasDBInitialize)
            Player.initializeTable()
    }

    // To check whether the DB has initialized
    def hasDBInitialize: Boolean = {
        DB getTable "Player" match {
            case Some(x) => true
            case None => false
        }
    }
}