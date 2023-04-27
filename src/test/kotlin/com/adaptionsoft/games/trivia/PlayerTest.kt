package com.adaptionsoft.games.trivia

import kotlin.test.*

class PlayerTest {

    private lateinit var player: Player

    @BeforeTest
    fun setup() {
        player = Player("Test Player")
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
}
