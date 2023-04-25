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
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
//    testImplementation( )"org.junit.jupiter:junit-jupiter:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}