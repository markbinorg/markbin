plugins {
    java
    id("org.graalvm.buildtools.native") version "1.1.2"
}

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(25))
    vendor.set(JvmVendorSpec.GRAAL_VM)
}

group = "com.boyninja1555"
version = "2"
val entry = "${group}.Main"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:26.1.0")
}

tasks.jar {
    manifest.attributes["Main-Class"] = entry
}

graalvmNative.binaries.named("main") {
    mainClass.set(entry)
    runtimeArgs.add("examples/hello.mkb")
    runtimeArgs.add("examples/hello.bin")
}
