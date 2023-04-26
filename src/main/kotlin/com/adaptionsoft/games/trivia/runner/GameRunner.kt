package com.adaptionsoft.games.trivia.runner

import com.adaptionsoft.games.uglytrivia.Game
import java.util.*

fun main(args: Array<String>) {

    val die = Random(args[0].toLong())
    val game = Game(die)

    game.addPlayer("Chet")
    game.addPlayer("Pat")
    game.addPlayer("Sue")

    game.run()
}

