import com.undefinedcreations.nova.ServerType

plugins {
    kotlin("jvm") version "2.3.0-Beta2"
    id("com.undefinedcreations.nova")
    id("com.gradleup.shadow") version "9.3.1"
}

group = "com.undefinedcreations"
version = "1.0.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveFileName.set("plugin.jar")
    }
    compileJava {
        options.release.set(25)
    }
    runServer {
        serverType(ServerType.PAPERMC)
        minecraftVersion("1.21.11")
        acceptMojangEula()
    }
}

java {
    disableAutoTargetJvm()
}

kotlin {
    jvmToolchain(25)
}