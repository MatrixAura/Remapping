# Remapping
A Gradle plugin that automatically re-obfuscates your jar according to the MCP obfuscation mappings.

## Imports
To import the plugin you will need to add this to your build.gradle.kts
```
id("dev.zl930.plugin") version "1.0.0"
```

## Configuration
```
remap {
    // This sets the mcp version
    version.set("1.8.9")
}