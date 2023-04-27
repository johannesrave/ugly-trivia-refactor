package com.adaptionsoft.games.trivia

import org.junit.jupiter.api.Disabled
import java.io.File
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GoldenMasterTest {
    private val numberOfMasters = 100

    @Test
    @Disabled("Golden Masters should be created once and then be left alone.")
    fun `writes goldenMaster to file`() {
        repeat(numberOfMasters) {
            val masterName = "testData/${it.toString().padStart(2, '0')}_goldenMaster.txt"
            val masterFile = PrintStream(masterName)

            System.setOut(masterFile)

            main(arrayOf(it.toString()))
        }
    }

    @Test
    fun `compares output to goldenMaster`() {

        val mismatches = mutableListOf<GoldenMasterMismatch>()

        repeat(numberOfMasters) { randomSeed ->

            val masterName = "testData/${randomSeed.toString().padStart(2, '0')}_goldenMaster.txt"
            val testResultName = "testData/${randomSeed.toString().padStart(2, '0')}_testResult.txt"

            ConsoleCaptor(testResultName).use {
                main(arrayOf(randomSeed.toString()))
            }

            val masterLines = File(masterName).readLines()
            val testResultLines = File(testResultName).readLines()


            assertEquals(masterLines.size, testResultLines.size, "Size mismatch in file $randomSeed")
            masterLines.forEachIndexed { lineIndex, _ ->
                if (masterLines[lineIndex] == testResultLines[lineIndex]) return@forEachIndexed

                mismatches.add(
                    GoldenMasterMismatch(
                        randomSeed,
                        lineIndex,
                        masterLines[lineIndex],
                        testResultLines[lineIndex]
                    )
                )
            }
        }

        mismatches.forEach { (fileIndex, lineIndex, masterLine, mismatchedLine) ->
            assertNull(
                Pair(fileIndex, lineIndex),
                "\n" +
                        "There is a mismatch in GoldenMaster $fileIndex on line $lineIndex:\n" +
                        "MASTER: $masterLine\n" +
                        "RESULT: $mismatchedLine\n"
            )
        }
    }

    data class GoldenMasterMismatch(
        val fileIndex: Int,
        val lineIndex: Int,
        val masterLine: String,
        val mismatchedLine: String
    )

    class ConsoleCaptor(fileName: String) : AutoCloseable {
        private var console: PrintStream? = System.out

        init {
            System.setOut(PrintStream(fileName))
        }

        override fun close() {
            System.setOut(console)
        }
    }
}
