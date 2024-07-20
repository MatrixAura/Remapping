plugins {
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "io.github.matrixaura"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.md-5:SpecialSource:1.11.4")
    compileOnly("org.projectlombok:lombok:1.18.4")
    annotationProcessor("org.projectlombok:lombok:1.18.4")
}


gradlePlugin {

    website.set("https://github.com/MatrixAura/Remapping")
    vcsUrl.set("https://github.com/MatrixAura/Remapping")

    plugins {
        create("RemappingPlugin") {
            id = "io.github.matrixaura.remapping"
            displayName = "Remapping Plugin"
            description = "This gradle plugin will remapped your generated jar with customizable mappings."
            tags = listOf("mapping", "utils", "remapper")
            implementationClass = "io.github.matrixaura.plugin.RemappingPlugin"
        }
    }
}
