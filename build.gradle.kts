import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

val kotlin_version: String = "1.8.20"

plugins {
    application
    kotlin("jvm") version "1.8.20"
}

application {
    mainClass.set("com.adaptionsoft.games.trivia.runner.GameRunnerKt")
}


repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
//    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED)
        showStandardStreams = true
    }
}

