package com.adaptionsoft.games.uglytrivia

import java.util.*

class Game(val seed: Int = Random().nextInt()) {
    private var players = ArrayList<Player>()
    private var places = IntArray(6)
    private var purses = IntArray(6)

    private var popQuestions = LinkedList<Any>()
    private var scienceQuestions = LinkedList<Any>()
    private var sportsQuestions = LinkedList<Any>()
    private var rockQuestions = LinkedList<Any>()

    private var currentPlayerIndex = 0
    private var isGettingOutOfPenaltyBox: Boolean = false

    init {
        for (i in 0..49) {
            popQuestions.addLast("Pop Question " + i)
            scienceQuestions.addLast("Science Question " + i)
            sportsQuestions.addLast("Sports Question " + i)
            rockQuestions.addLast("Rock Question " + i)
        }


    }
    fun addPlayer(player: Player) {
        players.add(player)
        places[howManyPlayers()] = 0
        purses[howManyPlayers()] = 0

        println(player.name + " was added")
        println("They are player number " + players.size)
    }

    private fun howManyPlayers(): Int {
        return players.size
    }

    fun roll(roll: Int) {
        var currentPlayer = players[currentPlayerIndex]
        println(currentPlayer.name + " is the current player")
        println("They have rolled a " + roll)

        if (currentPlayer.isInPenaltyBox) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true

                println(players[currentPlayerIndex].name + " is getting out of the penalty box")
                places[currentPlayerIndex] = places[currentPlayerIndex] + roll
                if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12

                println(
                    players[currentPlayerIndex].name
                            + "'s new location is "
                            + places[currentPlayerIndex]
                )
                println("The category is " + currentCategory())
                askQuestion()
            } else {
                println(players[currentPlayerIndex].name + " is not getting out of the penalty box")
                isGettingOutOfPenaltyBox = false
            }

        } else {

            places[currentPlayerIndex] = places[currentPlayerIndex] + roll
            if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12

            println(
                players[currentPlayerIndex].name
                        + "'s new location is "
                        + places[currentPlayerIndex]
            )
            println("The category is " + currentCategory())
            askQuestion()
        }

    }

    private fun askQuestion() {
        if (currentCategory() === "Pop")
            println(popQuestions.removeFirst())
        if (currentCategory() === "Science")
            println(scienceQuestions.removeFirst())
        if (currentCategory() === "Sports")
            println(sportsQuestions.removeFirst())
        if (currentCategory() === "Rock")
            println(rockQuestions.removeFirst())
    }

    private fun currentCategory(): String {
        if (places[currentPlayerIndex] == 0) return "Pop"
        if (places[currentPlayerIndex] == 4) return "Pop"
        if (places[currentPlayerIndex] == 8) return "Pop"
        if (places[currentPlayerIndex] == 1) return "Science"
        if (places[currentPlayerIndex] == 5) return "Science"
        if (places[currentPlayerIndex] == 9) return "Science"
        if (places[currentPlayerIndex] == 2) return "Sports"
        if (places[currentPlayerIndex] == 6) return "Sports"
        if (places[currentPlayerIndex] == 10) return "Sports"
        return "Rock"
    }

    fun isCorrectAnswer(): Boolean {
        var currentPlayer = players[currentPlayerIndex]
        if (currentPlayer.isInPenaltyBox) {
            if (isGettingOutOfPenaltyBox) {
                println("Answer was correct!!!!")
                purses[currentPlayerIndex]++
                println(
                    players[currentPlayerIndex].name
                            + " now has "
                            + purses[currentPlayerIndex]
                            + " Gold Coins."
                )

                val winner = didPlayerWin()
                currentPlayerIndex++
                if (currentPlayerIndex == players.size) currentPlayerIndex = 0

                return winner
            } else {
                currentPlayerIndex++
                if (currentPlayerIndex == players.size) currentPlayerIndex = 0
                return true
            }


        } else {

            println("Answer was corrent!!!!")
            purses[currentPlayerIndex]++
            println(
                players[currentPlayerIndex].name
                        + " now has "
                        + purses[currentPlayerIndex]
                        + " Gold Coins."
            )

            val winner = didPlayerWin()
            currentPlayerIndex++
            if (currentPlayerIndex == players.size) currentPlayerIndex = 0

            return winner
        }
    }

    fun isWrongAnswer(): Boolean {
        val currentPlayer = players[currentPlayerIndex]
        println("Question was incorrectly answered")
        println(players[currentPlayerIndex].name + " was sent to the penalty box")
        currentPlayer.isInPenaltyBox = true

        currentPlayerIndex++
        if (currentPlayerIndex == players.size) currentPlayerIndex = 0
        return true
    }

    private fun didPlayerWin(): Boolean {
        return purses[currentPlayerIndex] != 6
    }
}

data class Player(val name: String, var isInPenaltyBox: Boolean = false)