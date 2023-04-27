package com.adaptionsoft.games.trivia.runner

import com.adaptionsoft.games.uglytrivia.Game
import java.util.*

fun main(args: Array<String>) {

    val loadedDie = Random(args[0].toLong())
    val game = Game()
    game.playWith(loadedDie, "Chet", "Pat", "Sue")
}

