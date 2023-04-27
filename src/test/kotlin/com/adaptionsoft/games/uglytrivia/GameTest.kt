//package com.adaptionsoft.games.uglytrivia
//
//import org.junit.jupiter.api.Test
//import java.io.ByteArrayOutputStream
//import java.io.PrintStream
//import java.util.*
//import kotlin.test.assertEquals
//import kotlin.test.assertFalse
//import kotlin.test.assertTrue
//
//class GPTTests {
//
//    class GameTest {
//        @Test
//        fun `when a player is added, they are in the game and their name is printed`() {
//            val game = Game()
//            val playerName = "John"
//            val expectedOutput = "$playerName was added\nThey are player number 1\n"
//            val outputStream = ByteArrayOutputStream()
//            System.setOut(PrintStream(outputStream))
//
//            game.addPlayer(playerName)
//
//            assertEquals(game.players.size, 1)
//            assertEquals(outputStream.toString(), expectedOutput)
//        }
//
//        @Test
//        fun `when getNextPlayer is called, it returns the next player in the list`() {
//            val game = Game()
//            val player1 = Player("John")
//            val player2 = Player("Jane")
//            game.players = mutableListOf(player1, player2)
//
//            val result1 = game.getNextPlayer()
//            val result2 = game.getNextPlayer()
//
//            assertEquals(result1, player1)
//            assertEquals(result2, player2)
//        }
//
//        @Test
//        fun `when getNextPlayer is called and the current player is the last player in the list, it returns the first player`() {
//            val game = Game()
//            val player1 = Player("John")
//            val player2 = Player("Jane")
//            game.players = mutableListOf(player1, player2)
//            game.currentPlayerIndex = 1
//
//            val result = game.getNextPlayer()
//
//            assertEquals(result, player1)
//        }
//
//        @Test
//        fun `when a player takes a turn and answers correctly, they receive a coin`() {
//            val game = Game()
//            val player = Player("John")
//            val die = TestDie(listOf(1, 2, 3, 4, 5), listOf(0))
//
//            game.players = mutableListOf(player)
//            player.takeTurn(die)
//
//            assertEquals(player.coins, 1)
//        }
//
//        @Test
//        fun `when a player takes a turn and answers incorrectly, they are sent to the penalty box`() {
//            val game = Game()
//            val player = Player("John")
//            val die = TestDie(listOf(1, 2, 3, 4, 5), listOf(7))
//
//            game.players = mutableListOf(player)
//            player.takeTurn(die)
//
//            assertTrue(player.wasInPenaltyBox)
//        }
//
//        @Test
//        fun `when a player takes a turn and stays in the penalty box, they do not move or receive a coin`() {
//            val game = Game()
//            val player = Player("John", wasInPenaltyBox = true)
//            val die = TestDie(listOf(1, 2, 3, 4, 5), listOf(7))
//
//            game.players = mutableListOf(player)
//            player.takeTurn(die)
//
//            assertEquals(player.place, 0)
//            assertEquals(player.coins, 0)
//        }
//
//        @Test
//        fun `when a player takes a turn and leaves the penalty box, they move and receive a coin if they answer correctly`() {
//            val game = Game()
//            val player = Player("John", wasInPenaltyBox = true)
//            val die = TestDie(listOf(1), listOf(1))
//
//            game.players = mutableListOf(player)
//            player.takeTurn(die)
//
//            assertFalse(player.wasInPenaltyBox)
//            assertEquals(player.place, 1)
//            assertEquals(player.coins, 1)
//        }
//}