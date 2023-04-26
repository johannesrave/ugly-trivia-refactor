package com.adaptionsoft.games.trivia.runner

import com.adaptionsoft.games.uglytrivia.Game
import java.util.*

object GameRunner {
    var notAWinner: Boolean = false
}

fun main(args: Array<String>) {
    val rand = Random()
    rand.setSeed(args[0].toLong())


    // seed in game übergeben
    val game = Game()

    aGame.add("Chet")
    aGame.add("Pat")
    aGame.add("Sue")


    // es wird gewürfelt und roll() läuft durch
    // es wird NOCHMAL gewürfelt, davon abhängig ist die antwort falsch oder wahr
    // abhängig von der antwort endet das spiel


    // was ich hier gerne lesen würde:
    // while (aGame.isNotFinished()) aGame.makeTurn()

    do {

        game.roll(rand.nextInt(5) + 1)

        if (rand.nextInt(9) == 7) {
            GameRunner.notAWinner = aGame.wrongAnswer()
        } else {
            GameRunner.notAWinner = aGame.wasCorrectlyAnswered()
        }


    } while (GameRunner.notAWinner)

}
