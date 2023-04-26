package com.adaptionsoft.games.uglytrivia

import java.util.*

class Game(private val die: Random = Random()) {
    private var players = emptyList<Player>().toMutableList()
    private var currentPlayerIndex = -1

    fun run() {
        Category.reset()

        var player: Player
        do {
            player = getNextPlayer()
            player.takeTurn(die)
        } while (player.coins < 6)
    }

    private fun getNextPlayer(): Player {
        currentPlayerIndex = ++currentPlayerIndex % players.size
        return players[currentPlayerIndex]
    }

    fun addPlayer(playerName: String) {
        players.add(Player(playerName))
        println("$playerName was added")
        println("They are player number ${players.size}")
    }
}

data class Player(val name: String, var wasInPenaltyBox: Boolean = false, var coins: Int = 0, var place: Int = 0) {
    private fun calculateAndSetPlace(roll: Int) {
        place = (place + roll) % 12
    }

    fun takeTurn(die: Random) {
        val placeRoll = die.nextInt(5) + 1
        val answerRoll = die.nextInt(9)
        val answerIsCorrect = answerRoll != 7
        val canLeavePenaltyBox = placeRoll % 2 != 0
        val leavesPenaltyBox = wasInPenaltyBox && canLeavePenaltyBox
        val staysInPenaltyBox = wasInPenaltyBox && !canLeavePenaltyBox

        if (!staysInPenaltyBox) {
            calculateAndSetPlace(placeRoll)

            if (answerIsCorrect) {
                coins++
            } else {
                wasInPenaltyBox = true
            }
        }

        println(buildTurnSummary(answerIsCorrect, leavesPenaltyBox, staysInPenaltyBox, placeRoll))
        return
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
        fun reset() = values().forEach { it.questions = it.questionCards.listIterator() }

        fun countOffByPlace(place: Int): Category = Category.values().find { place % 4 == it.ordinal }
            ?: throw IllegalStateException()

        fun nextQuestionByPlace(place: Int): String = countOffByPlace(place).questions.next()
    }
}