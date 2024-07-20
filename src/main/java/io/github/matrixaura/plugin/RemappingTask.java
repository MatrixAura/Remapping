package io.github.matrixaura.plugin;

import lombok.Getter;
import net.md_5.specialsource.Jar;
import net.md_5.specialsource.JarMapping;
import net.md_5.specialsource.JarRemapper;
import net.md_5.specialsource.provider.JarProvider;
import net.md_5.specialsource.provider.JointProvider;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;

import java.io.File;
import java.nio.file.Files;

public class RemappingTask extends DefaultTask {

    @Getter
    @Input
    public Property<File> mapping = getProject().getObjects().property(File.class);

    @Getter
    @OutputFile
    public Provider<File> outputFile = getProject().provider(() -> new File(getProject().getLayout().getBuildDirectory().getAsFile().get(), getProject().getName() + "-" + getProject().getVersion() + ".jar"));

    @TaskAction
    public void execute() throws Exception {
        AbstractArchiveTask task = (AbstractArchiveTask) getProject().getTasks().named("jar").get();
        File archiveFile = task.getArchiveFile().get().getAsFile();
        assert mapping != null;

        File tempFile = Files.createTempFile(null, ".jar").toFile();

        try (Jar inputJar = Jar.init(archiveFile)) {
            JarMapping jarMapping = new JarMapping();
            jarMapping.loadMappings(mapping.get().getAbsolutePath(), false, false, null, null);

            JointProvider provider = new JointProvider();
            provider.add(new JarProvider(inputJar));
            jarMapping.setFallbackInheritanceProvider(provider);

            JarRemapper remapper = new JarRemapper(jarMapping);
            remapper.remapJar(inputJar, tempFile);
        }

        Files.copy(tempFile.toPath(), archiveFile.toPath());

        outputFile = getProject().provider(() -> tempFile);
        tempFile.delete();
        System.out.println("Successfully remapped jar: " + getProject().getPath());
    }

}
