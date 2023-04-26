package com.adaptionsoft.games.uglytrivia

import java.util.*

class Game {
    private var players = emptyList<Player>().toMutableList()
    private var currentPlayerIndex = 0
    private val die = Random()

    fun run() {
        Category.reset()

        while (makeTurn()) {
        }
    }

    fun setDieSeed(seed: Long) {
        die.setSeed(seed)
    }

    fun addPlayer(player: Player) {
        players.add(player)
        println("${player.name} was added")
        println("They are player number ${players.size}")
    }

    private fun makeTurn(): Boolean {
        val player = players[currentPlayerIndex]
        currentPlayerIndex = ++currentPlayerIndex % players.size

        val placeRoll = die.nextInt(5) + 1
        val answerRoll = die.nextInt(9)
        val answerIsIncorrect = answerRoll == 7
        val isGettingOutOfPenaltyBox = placeRoll % 2 != 0

        println("${player.name} is the current player")
        println("They have rolled a $placeRoll")

        if (player.isInPenaltyBox && !isGettingOutOfPenaltyBox && answerIsIncorrect) {
            println("${player.name} is not getting out of the penalty box")
            println("Question was incorrectly answered")
            println("${player.name} was sent to the penalty box")
            return true
        } else if (player.isInPenaltyBox && !isGettingOutOfPenaltyBox) {
            println("${player.name} is not getting out of the penalty box")
            return true
        } else if (player.isInPenaltyBox) {
            println("${player.name} is getting out of the penalty box")
        }

        player.calculateAndSetPlace(placeRoll)

        println("${player.name}'s new location is ${player.place}")
        println("The category is ${Category.countOffByPlace(player.place)}")
        println(Category.nextQuestionByPlace(player.place))

        if (answerIsIncorrect) {
            println("Question was incorrectly answered")
            println("${player.name} was sent to the penalty box")
            player.isInPenaltyBox = true

            return true
        }


        // preserve bug to match with golden master
        println("Answer was ${if (player.isInPenaltyBox) "correct" else "corrent"}!!!!")
        player.coins++
        println("${player.name} now has ${player.coins} Gold Coins.")

        return player.coins < 6
    }
}

data class Player(val name: String, var isInPenaltyBox: Boolean = false, var coins: Int = 0, var place: Int = 0) {
    fun calculateAndSetPlace(roll: Int) {
        place = (place + roll) % 12
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