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
            player.takeTurn(placeRoll, answerRoll)
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

data class Player(val name: String, var wasInPenaltyBox: Boolean = false, var coins: Int = 0, var place: Int = 0) {
    fun takeTurn(placeRoll: Int, answerRoll: Int) {
        val answerIsCorrect = answerRoll != 7
        val canLeavePenaltyBox = placeRoll % 2 != 0
        val leavesPenaltyBox = wasInPenaltyBox && canLeavePenaltyBox
        val staysInPenaltyBox = wasInPenaltyBox && !canLeavePenaltyBox

        if (!staysInPenaltyBox) {
            countOffPlaceByRoll(placeRoll)

            if (answerIsCorrect) {
                coins++
            } else {
                wasInPenaltyBox = true
            }
        }

        println(buildTurnSummary(answerIsCorrect, leavesPenaltyBox, staysInPenaltyBox, placeRoll))
        return
    }

    private fun countOffPlaceByRoll(roll: Int) {
        place = (place + roll) % 12
    }

    private fun buildTurnSummary(
        answerIsCorrect: Boolean,
        leftPenaltyBox: Boolean,
        staysInPenaltyBox: Boolean,
        placeRoll: Int
    ): String {
        var summary = "$name is the current player" +
                "\nThey have rolled a $placeRoll"

        if (staysInPenaltyBox) {
            summary += "\n$name is not getting out of the penalty box"
            if (!answerIsCorrect) {
                summary += "\nQuestion was incorrectly answered"
                summary += "\n$name was sent to the penalty box"
            }
            return summary.trimStart()
        }
        if (leftPenaltyBox) {
            summary += "\n$name is getting out of the penalty box"
        }
        summary += "\n$name's new location is $place" +
                "\nThe category is ${Category.countOffByPlace(place)}" +
                "\n" + Category.nextQuestionByPlace(place)

        if (answerIsCorrect) {
            summary += "\nAnswer was ${if (wasInPenaltyBox) "correct" else "corrent"}!!!!" +
                    "\n$name now has $coins Gold Coins."
        } else {
            summary += "\nQuestion was incorrectly answered" +
                    "\n$name was sent to the penalty box"
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