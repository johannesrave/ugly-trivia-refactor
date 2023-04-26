package com.adaptionsoft.games.trivia.runner

import com.adaptionsoft.games.uglytrivia.Game
import com.adaptionsoft.games.uglytrivia.Player

fun main(args: Array<String>) {

    val game = Game()
    game.setDieSeed(args[0].toLong())

    game.addPlayer(Player("Chet"))
    game.addPlayer(Player("Pat"))
    game.addPlayer(Player("Sue"))

    game.run()
}

