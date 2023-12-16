plugins {
    `maven-publish`
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21"
}

group = "com.github.kimcore"
version = "1.7"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    val ktorVersion = "2.3.7"
    implementation("io.ktor", "ktor-client", ktorVersion)
    implementation("io.ktor", "ktor-client-serialization", ktorVersion)
    implementation("io.ktor", "ktor-client-content-negotiation", ktorVersion)
    implementation("io.ktor", "ktor-serialization", ktorVersion)
    implementation("io.ktor", "ktor-serialization-kotlinx-json", ktorVersion)

    val serializationVersion = "1.6.2"
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", serializationVersion)
}

tasks {
    compileJava {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

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
