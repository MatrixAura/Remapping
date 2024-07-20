# Remapping
A Gradle plugin that automatically re-obfuscates your jar according to the MCP obfuscation mappings.

## Imports
To import the plugin you will need to add this to your build.gradle(.kts)

kotlin
```kotlin
id("io.github.matrixaura.remapping") version "1.0.0"
```
groovy
```groovy
id 'io.github.matrixaura.remapping' version '1.0.0'
```
## Configuration (Example)
groovy
```groovy
remap {
    // This sets the mapping file
    mapping.set(file('src/main/resources/foo/bar/mapping.srg'))
}
```
kotlin
```kotlin
remap {
    // This sets the mapping file
    mapping.set(file("src/main/resources/foo/bar/mapping.srg"))
}
```