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

data class Player(val name: String, var isInPenaltyBox: Boolean = false, var coins: Int = 0, var place: Int = 0) {
    fun calculateAndSetPlace(roll: Int) {
        place = (place + roll) % 12
    }

    fun takeTurn(die: Random) {

        val placeRoll = die.nextInt(5) + 1
        val answerRoll = die.nextInt(9)
        val answerIsIncorrect = answerRoll == 7
        val staysInPenaltyBox = placeRoll % 2 == 0

        println("$name is the current player")
        println("They have rolled a $placeRoll")

        if (isInPenaltyBox && staysInPenaltyBox && answerIsIncorrect) {
            println("$name is not getting out of the penalty box")
            println("Question was incorrectly answered")
            println("$name was sent to the penalty box")
            return
        } else if (isInPenaltyBox && staysInPenaltyBox) {
            println("$name is not getting out of the penalty box")
            return
        } else if (isInPenaltyBox) {
            println("$name is getting out of the penalty box")
        }

        calculateAndSetPlace(placeRoll)

        println("$name's new location is $place")
        println("The category is ${Category.countOffByPlace(place)}")
        println(Category.nextQuestionByPlace(place))

        if (answerIsIncorrect) {

            isInPenaltyBox = true

//            return
        } else {
            coins++
        }


        // preserve bug to match with golden master
//        println()
//        println("$name now has $coins Gold Coins.")

        println(buildTurnSummary(answerIsIncorrect))
        return
    }

    private fun buildTurnSummary(answerIsIncorrect: Boolean): String {
        if (answerIsIncorrect) {
            return "Question was incorrectly answered\n" +
                    "$name was sent to the penalty box"
        }

        return "Answer was ${if (isInPenaltyBox) "correct" else "corrent"}!!!!\n" +
                "$name now has $coins Gold Coins."
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