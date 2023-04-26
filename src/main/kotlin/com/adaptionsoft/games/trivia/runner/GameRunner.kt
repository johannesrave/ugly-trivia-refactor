package com.adaptionsoft.games.trivia.runner

import com.adaptionsoft.games.uglytrivia.Game
import com.adaptionsoft.games.uglytrivia.Player
import java.util.*

object GameRunner {
    var isGameInProgress: Boolean = false
}

fun main(args: Array<String>) {
    val rand = Random()
    rand.setSeed(args[0].toLong())


    // seed in game übergeben
    val game = Game()

    game.addPlayer(Player("Chet"))
    game.addPlayer(Player("Pat"))
    game.addPlayer(Player("Sue"))


    // es wird gewürfelt und roll() läuft durch
    // es wird NOCHMAL gewürfelt, davon abhängig ist die antwort falsch oder wahr
    // abhängig von der antwort endet das spiel


    // was ich hier gerne lesen würde:
    // while (aGame.isNotFinished()) aGame.makeTurn()

    do {

        game.roll(rand.nextInt(5) + 1)

        if (rand.nextInt(9) == 7) {
            GameRunner.isGameInProgress = game.isWrongAnswer()
        } else {
            GameRunner.isGameInProgress = game.isCorrectAnswer()
        }


    } while (GameRunner.isGameInProgress)

}
