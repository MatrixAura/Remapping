package dev.zl930.remapping;

import lombok.Getter;
import lombok.Setter;
import net.md_5.specialsource.Jar;
import net.md_5.specialsource.JarMapping;
import net.md_5.specialsource.JarRemapper;
import net.md_5.specialsource.provider.JarProvider;
import net.md_5.specialsource.provider.JointProvider;
import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;

import java.io.File;
import java.nio.file.Files;

public class RemappingTask extends DefaultTask {

    public static final String MAPPING_DEP = "net.minecraft:mappings_stable:{VERSION}@zip";
    public static String getMappingDep(String version) {
        return MAPPING_DEP.replace("{VERSION}", version);
    }


    @Getter
    @Input
    Property<String> version;

    @Setter
    @Getter
    @OutputFile
    Provider<File> outputFile = getProject().provider(() -> new File(getProject().getLayout().getBuildDirectory().getAsFile().get(), getProject().getName() + "-" + getProject().getVersion() + ".jar"));

    @TaskAction
    public void execute() throws Exception {
        AbstractArchiveTask task = (AbstractArchiveTask) getProject().getTasks().named("jar").get();
        File archiveFile = task.getArchiveFile().get().getAsFile();
        assert getVersion() != null;
        String version = this.getVersion().get();

        File tempFile = Files.createTempFile(null, ".jar").toFile();

        DependencyHandler dependencies = getProject().getDependencies();
        Configuration mappingConfig = getProject().getConfigurations().detachedConfiguration(
                dependencies.create(getMappingDep(version))
        );
        File mappingFile = mappingConfig.getSingleFile();

        try (Jar inputJar = Jar.init(archiveFile)) {
            JarMapping mapping = new JarMapping();
            mapping.loadMappings(mappingFile.getAbsolutePath(), true, false, null, null);

            JointProvider provider = new JointProvider();
            provider.add(new JarProvider(inputJar));
            mapping.setFallbackInheritanceProvider(provider);

            JarRemapper remapper = new JarRemapper(mapping);
            remapper.remapJar(inputJar, tempFile);
        }

        Files.copy(tempFile.toPath(), archiveFile.toPath());

        setOutputFile(getProject().provider(() -> tempFile));
        tempFile.delete();
        System.out.println("Successfully remapped jar: " + getProject().getPath());
    }

}
