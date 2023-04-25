package com.adaptionsoft.games.trivia

import com.adaptionsoft.games.trivia.runner.main
import java.io.File
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertTrue

class SomeTest {

    @Test
    fun true_is_true() {
        assertTrue(false)
    }
}

class GoldenMasterTest {
//    @Test
//    fun `writes goldenMaster to file`() {
//
//        repeat(10) {
//            val masterName = "testData/${it.toString().padStart(2, '0')}_goldenMaster.txt"
//            val masterFile = PrintStream(masterName)
//
//            System.setOut(masterFile)
//
//            main(arrayOf(it.toString()))
//        }
//    }


    @Test
    fun `compares output to goldenMaster`() {

        repeat(10) {
            val masterName = "testData/${it.toString().padStart(2, '0')}_goldenMaster.txt"
            val testResultName = "testData/${it.toString().padStart(2, '0')}_testResult.txt"

            val result = PrintStream(testResultName)

            System.setOut(result)

            main(arrayOf(it.toString()))

            val masterLines = File(masterName).readLines()
            val testResultLines = File(masterName).readLines()

            val erroneousLines = emptyList<Triple<Int, String, String>>().toMutableList()
            masterLines.forEachIndexed { index, _ ->
                if (masterLines[index] != testResultLines[index]) {
                    erroneousLines.add(Triple(index, masterLines[index], testResultLines[index]))
                }
            }

            assertTrue(erroneousLines.isEmpty())
        }
    }
}
