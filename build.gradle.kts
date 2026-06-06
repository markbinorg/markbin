plugins {
    id("java")
    id("application")
    kotlin("jvm")
}

group = "com.boyninja1555"
version = "1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:26.1.0")
    implementation(kotlin("stdlib-jdk8"))
}

application {
    mainClass = "${group}.markbin.Main"
}
kotlin {
    jvmToolchain(25)
}