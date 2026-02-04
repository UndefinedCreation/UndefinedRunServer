import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.3.0-Beta2"
    id("maven-publish")
    id("com.gradle.plugin-publish") version "2.0.0"
    id("com.gradleup.shadow") version "9.3.1"
}

group = properties["group"]!!
version = properties["version"]!!

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib"))
    implementation("net.md-5:SpecialSource:1.11.4")
    implementation("com.google.code.gson:gson:2.12.1")
}

gradlePlugin {
    website = "https://discord.undefinedcreations.com/"
    vcsUrl = "https://github.com/UndefinedCreations/Nova"

    plugins {
        register("nova") {
            id = "com.undefinedcreations.nova"
            displayName = "Nova"
            description = "Allows you to run different type of minecraft servers as a Gradle task."
            tags.addAll("spigot", "mapping", "NMS", "mojang", "utils", "server", "runServer", "paper", "pufferfishmc", "purpur", "bungeecord", "waterfall")
            implementationClass = "com.undefinedcreations.nova.NovaPlugin"
        }
    }
}

tasks {
    compileKotlin {
        compilerOptions.jvmTarget = JvmTarget.JVM_1_8
    }
    compileJava {
        options.release = 8
    }
    shadowJar {
        archiveClassifier = ""
    }
}

java {
    disableAutoTargetJvm()
}

kotlin {
    jvmToolchain(25)
}