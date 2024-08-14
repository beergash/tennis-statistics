package it.beergash.tennis.data.scraper;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = { "it.beergash.tennis.data.scraper","it.beergash.reader.writer.api"},
                       exclude = {DataSourceAutoConfiguration.class })
@EnableBatchProcessing
public class TennisDataScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(TennisDataScraperApplication.class, args);
    }
}
