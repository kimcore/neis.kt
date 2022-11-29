plugins {
    `maven-publish`
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
    id("com.github.otkmnb2783.dotenv") version "0.2.0"
}

group = "com.github.kimcore"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.ktor", "ktor-client", "2.1.3")
    implementation("io.ktor", "ktor-client-cio", "2.1.3")
    implementation("io.ktor", "ktor-client-serialization", "2.1.3")
    implementation("io.ktor", "ktor-client-content-negotiation", "2.1.3")
    implementation("io.ktor", "ktor-serialization", "2.1.3")
    implementation("io.ktor", "ktor-serialization-kotlinx-json", "2.1.3")

    implementation("org.jsoup", "jsoup", "1.15.3")

    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.4.1")
    implementation("org.slf4j", "slf4j-api", "2.0.5")
    implementation("ch.qos.logback", "logback-classic", "1.4.5")
    implementation("io.github.microutils", "kotlin-logging-jvm", "3.0.4")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

@Suppress("UNCHECKED_CAST")
val envMap = env as MutableMap<String, String>

publishing {
    publications {
        create<MavenPublication>("neis.kt") {
            from(components["java"])

            group = project.group as String
            version = project.version as String
            artifactId = project.name
            artifact(sourcesJar)
        }
    }
}