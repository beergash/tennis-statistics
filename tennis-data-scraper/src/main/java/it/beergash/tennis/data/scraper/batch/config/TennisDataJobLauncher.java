package it.beergash.tennis.data.scraper.batch.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TennisDataJobLauncher {

    private final Job job;

    private final JobLauncher jobLauncher;

    @Autowired
    TennisDataJobLauncher(@Qualifier("tennisDataJob") Job job, JobLauncher jobLauncher) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        this.job = job;
        this.jobLauncher = jobLauncher;
        jobLauncher.run(job, getJobParameters());
    }

    private JobParameters getJobParameters() {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        return jobParametersBuilder.toJobParameters();
    }
}
