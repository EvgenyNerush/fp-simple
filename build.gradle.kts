plugins {
    kotlin("jvm") version "1.4.32"
}

version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:3.0.2")
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.1.0")
}
