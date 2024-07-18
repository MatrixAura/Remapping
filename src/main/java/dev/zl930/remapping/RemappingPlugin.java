package dev.zl930.remapping;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class RemappingPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project target) {
        target.getTasks().register("remap", RemappingTask.class, it -> it.setGroup("build")).get().dependsOn(target.getTasks().named("jar").get());
    }
}
