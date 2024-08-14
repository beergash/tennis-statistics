package it.beergash.tennis.data.scraper.batch.config;

import it.beergash.data.common.repository.model.Match;
import it.beergash.tennis.data.scraper.batch.reader.SourceCsvDataReader;
import it.beergash.tennis.data.scraper.batch.writer.CsvTennisDataWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TennisDataJobConfig {

    @Autowired
    private CsvTennisDataWriter dataWriter;

    @Autowired
    private SourceCsvDataReader sourceCsvDataReader;

    @Bean
    public Step tennisDataStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("tennisDataStep")
                .<List<Match>, List<Match>>chunk(1)
                .reader(sourceCsvDataReader)
                .writer(dataWriter)
                .build();
    }

    @Bean
    public Job tennisDataJob(JobBuilderFactory jobBuilderFactory,
                            @Qualifier("tennisDataStep") Step tennisDataStep) {
        return jobBuilderFactory.get("tennisDataStep")
                .incrementer(new RunIdIncrementer())
                .flow(tennisDataStep)
                .end()
                .build();
    }

}
