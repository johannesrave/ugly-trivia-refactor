package com.adaptionsoft.games.trivia

import java.util.*

class Game {
    fun playWith(die: Random = Random(), vararg playerNames: String) {
        Category.resetAllQuestions()
        announcePlayers(playerNames.toList())
        val players = setupPlayers(playerNames.toList())
        val diceRolls = setupDiceRolls(die)

        var player: Player
        do {
            player = players.next()
            val (placeRoll, answerRoll) = diceRolls.next()
            val turnSummary = player.takeTurn(placeRoll, answerRoll)
            println(turnSummary)
        } while (player.coins < 6)
    }

    private fun announcePlayers(playerNames: List<String>) = playerNames
        .forEachIndexed { i, name -> println("$name was added\nThey are player number ${i + 1}") }

    private fun setupPlayers(playerNames: List<String>): Iterator<Player> {
        val players = playerNames.map { name -> Player(name) }
        return generateSequence(0) { (it + 1) % playerNames.size }.map { i -> players[i] }.iterator()
    }

    private fun setupDiceRolls(die: Random): Iterator<Pair<Int, Int>> =
        generateSequence { Pair(die.nextInt(5) + 1, die.nextInt(9)) }.iterator()
}

data class Player(val name: String, var inPenaltyBox: Boolean = false, var coins: Int = 0, var place: Int = 0) {
    fun takeTurn(placeRoll: Int, answerRoll: Int): TurnSummary {
        val answerIsCorrect = answerRoll != 7
        val canLeavePenaltyBox = placeRoll % 2 != 0
        val leavesPenaltyBox = inPenaltyBox && canLeavePenaltyBox
        val staysInPenaltyBox = inPenaltyBox && !canLeavePenaltyBox

        if (!staysInPenaltyBox) {
            countOffPlaceByRoll(placeRoll)

            if (answerIsCorrect) {
                coins++
            } else {
                inPenaltyBox = true
            }
        }

        return TurnSummary(this, answerIsCorrect, leavesPenaltyBox, staysInPenaltyBox, placeRoll)
    }

    private fun countOffPlaceByRoll(roll: Int) {
        place = (place + roll) % 12
    }
}

data class TurnSummary(
    val player: Player,
    val answerIsCorrect: Boolean,
    val leavesPenaltyBox: Boolean,
    val staysInPenaltyBox: Boolean,
    val placeRoll: Int
) {
    override fun toString(): String {
        var summary = "${player.name} is the current player" +
                "\nThey have rolled a $placeRoll"

        if (staysInPenaltyBox) {
            summary += "\n${player.name} is not getting out of the penalty box"
            if (!answerIsCorrect) {
                summary += "\nQuestion was incorrectly answered"
                summary += "\n${player.name} was sent to the penalty box"
            }
            return summary.trimStart()
        }
        if (leavesPenaltyBox) {
            summary += "\n${player.name} is getting out of the penalty box"
        }
        summary += "\n${player.name}'s new location is ${player.place}" +
                "\nThe category is ${Category.countOffByPlace(player.place)}" +
                "\n" + Category.nextQuestionByPlace(player.place)

        if (answerIsCorrect) {
            summary += "\nAnswer was ${if (player.inPenaltyBox) "correct" else "corrent"}!!!!" +
                    "\n${player.name} now has ${player.coins} Gold Coins."
        } else {
            summary += "\nQuestion was incorrectly answered" +
                    "\n${player.name} was sent to the penalty box"
        }

        return summary.trimStart()
    }
}

enum class Category {
    Pop, Science, Sports, Rock;

    private val questionCards = (0..49).map { "$name Question $it" }
    var questions = questionCards.listIterator()

    companion object {
        fun resetAllQuestions() = values().forEach { it.questions = it.questionCards.listIterator() }

        fun countOffByPlace(place: Int): Category = Category.values().find { place % 4 == it.ordinal }
            ?: throw IllegalStateException()

        fun nextQuestionByPlace(place: Int): String = countOffByPlace(place).questions.next()
    }
}
