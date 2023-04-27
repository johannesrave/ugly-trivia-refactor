package com.adaptionsoft.games.trivia

import kotlin.test.*

class PlayerTest {

    private lateinit var player: Player

    @BeforeTest
    fun setup() {
        player = Player("Test Player")
        Category.resetAllQuestions()
    }
    @Test
    fun `takeTurn should update player's coins if answer is correct`() {
        player.takeTurn(4, 6)
        assertEquals(1, player.coins)
    }

    @Test
    fun `takeTurn should not update player's coins if answer is incorrect`() {
        player.takeTurn(2, 7)
        assertEquals(0, player.coins)
    }

    @Test
    fun `takeTurn should move player to correct location based on place roll`() {
        player.takeTurn(7, 2)
        assertEquals(7, player.place)
    }

    @Test
    fun `takeTurn should not set inPenaltyBox to true if answer is correct and player is not in penalty box`() {
        player.takeTurn(3, 5)
        assertFalse(player.inPenaltyBox)
    }

    @Test
    fun `takeTurn should set inPenaltyBox to true if answer is incorrect and player is not in penalty box`() {
        player.takeTurn(3, 7)
        assertTrue(player.inPenaltyBox)
    }

    @Test
    fun `takeTurn should not update player's coins if answer is incorrect and player is in penalty box`() {
        player.inPenaltyBox = true
        player.takeTurn(2, 7)
        assertEquals(0, player.coins)
    }
    @Test
    fun `takeTurn should return correct TurnSummary if player stays in the penalty box`() {
        player.inPenaltyBox = true
        val turnSummary = player.takeTurn(4, 6)
        assertEquals(player, turnSummary.player)
        assertTrue(turnSummary.answerIsCorrect)
        assertFalse(turnSummary.leavesPenaltyBox)
        assertTrue(turnSummary.staysInPenaltyBox)
        assertEquals(4, turnSummary.placeRoll)
    }
}


class TurnSummaryTest {
    @BeforeTest
    fun setup() {
        Category.resetAllQuestions()
    }
    @Test
    fun `toString should return correct string when player stays in penalty box`() {
        val player = Player("Test Player", inPenaltyBox = true)
        val summary = TurnSummary(player,
            answerIsCorrect = true,
            leavesPenaltyBox = false,
            staysInPenaltyBox = true,
            placeRoll = 4
        )
        val expected = "Test Player is the current player\n" +
                "They have rolled a 4\n" +
                "Test Player is not getting out of the penalty box"
        assertEquals(expected, summary.toString())
    }

    @Test
    fun `toString should return correct string when player leaves penalty box and answer is correct`() {
        val player = Player("Test Player", inPenaltyBox = true, coins = 1, place = 3)
        val summary = TurnSummary(player,
            answerIsCorrect = true,
            leavesPenaltyBox = true,
            staysInPenaltyBox = false,
            placeRoll = 5
        )
        val expected = "Test Player is the current player\n" +
                "They have rolled a 5\n" +
                "Test Player is getting out of the penalty box\n" +
                "Test Player's new location is 3\n" +
                "The category is Rock\n" +
                "Rock Question 0\n" +
                "Answer was correct!!!!\n" +
                "Test Player now has 1 Gold Coins."
        assertEquals(expected, summary.toString())
    }

    @Test
    fun `toString should return correct string when player leaves penalty box and answer is incorrect`() {
        val player = Player("Test Player", inPenaltyBox = true, place = 7)
        val summary = TurnSummary(player,
            answerIsCorrect = false,
            leavesPenaltyBox = true,
            staysInPenaltyBox = false,
            placeRoll = 4
        )
        val expected = "Test Player is the current player\n" +
                "They have rolled a 4\n" +
                "Test Player is getting out of the penalty box\n" +
                "Test Player's new location is 7\n" +
                "The category is Rock\n" +
                "Rock Question 0\n" +
                "Question was incorrectly answered\n" +
                "Test Player was sent to the penalty box"
        assertEquals(expected, summary.toString())
    }
}