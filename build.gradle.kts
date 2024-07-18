plugins {
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "dev.zl930"
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
        create("remapping") {
            id = "dev.zl930.remapping"
            displayName = "Remapping"
            description = "This gradle plugin will remapped your projects using mcp-to-searge mapping."
            tags = listOf("searge", "mapping", "mojang", "utils", "remapper")
            implementationClass = "dev.zl930.remapping.RemappingPlugin"
        }
    }

}
