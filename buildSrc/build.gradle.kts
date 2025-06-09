plugins {
    `kotlin-dsl`
}

dependencies {
    testImplementation(libs.javafaker)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.launcher)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    maxHeapSize = "1G"
    testLogging {
        events("passed")
    }
}
