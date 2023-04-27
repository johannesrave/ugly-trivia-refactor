package com.adaptionsoft.games.trivia

import java.util.*

fun main(args: Array<String>) {

    val loadedDie = Random(args[0].toLong())
    val game = Game()
    game.playWith(loadedDie, "Chet", "Pat", "Sue")
}

