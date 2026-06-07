plugins {
    id("java")
    id("application")
    id("org.graalvm.buildtools.native") version "1.1.1"
}

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(25))
    vendor.set(JvmVendorSpec.GRAAL_VM)
}

group = "com.boyninja1555"
version = "1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:26.1.0")
}

application {
    mainClass.set("${group}.markbin.Main")
}