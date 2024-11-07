package example.billingjob;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class FilePreparationTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
        String inputFile = jobParameters.getString("input.file");
        Path source = Paths.get(inputFile);
        Path target = Paths.get("staging", source.toFile().getName());
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

        // Path source = Paths.get("src/main/resources/billing-2023-01.csv");
        // Path target = Paths.get("staging", source.getFileName().toString());

        // try {
        //     // Ensure target directory exists
        //     Path targetDir = target.getParent();
        //     if (!Files.exists(targetDir)) {
        //         Files.createDirectories(targetDir);
        //     }

        //     // Perform the copy operation
        //     Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        // } catch (NoSuchFileException e) {
        //     System.err.println("Source file not found: " + e.getMessage());
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        return RepeatStatus.FINISHED;
    }
}
