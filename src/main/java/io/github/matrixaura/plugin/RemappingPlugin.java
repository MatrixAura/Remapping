package io.github.matrixaura.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class RemappingPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project target) {
        target.getTasks().register("remap", RemappingTask.class, it -> it.setGroup("build")).get().dependsOn(target.getTasks().named("jar").get());
    }
}
