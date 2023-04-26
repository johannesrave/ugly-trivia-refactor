package com.adaptionsoft.games.uglytrivia

import org.junit.jupiter.api.Test
import java.util.*

class GameTest {

    @Test
    fun rollBehavesCorrectly() {
        val aGame = Game()
        val rand = Random()
        rand.setSeed(1)

        aGame.addPlayer(Player("Chet"))
        aGame.addPlayer(Player("Pat"))
        aGame.addPlayer(Player("Sue"))

        aGame.takeTurn()

        assert(aGame.isWrongAnswer())

        aGame.takeTurn()

        assert(aGame.isCorrectAnswer())

        aGame.takeTurn()

        assert(aGame.isWrongAnswer())
    }

}