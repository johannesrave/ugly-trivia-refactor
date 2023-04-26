package com.adaptionsoft.games.uglytrivia

import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun rollBehavesCorrectly() {
        val aGame = Game()

        aGame.addPlayer(Player("Chet"))
        aGame.addPlayer(Player("Pat"))
        aGame.addPlayer(Player("Sue"))

        aGame.roll(1)

        assert(aGame.isWrongAnswer())

        aGame.roll(2)

        assert(aGame.isCorrectAnswer())

        aGame.roll(7)

        assert(aGame.isWrongAnswer())
    }

    @Test
    fun rollBehavesCorrectly2() {
        val aGame = Game()

        aGame.addPlayer(Player("Chet"))
        aGame.addPlayer(Player("Pat"))
        aGame.addPlayer(Player("Sue"))

        aGame.roll(4)

        assert(aGame.isWrongAnswer())

        aGame.roll(1)

        assert(aGame.isCorrectAnswer())

        aGame.roll(5)

        assert(aGame.isWrongAnswer())
    }
}