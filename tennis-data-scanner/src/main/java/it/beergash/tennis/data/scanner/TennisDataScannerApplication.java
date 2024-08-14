package it.beergash.tennis.data.scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = { "it.beergash.reader.writer.api", "it.beergash.tennis.data.scanner","it.beergash.api.common"}
,exclude = {DataSourceAutoConfiguration.class })
public class TennisDataScannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TennisDataScannerApplication.class, args);
    }
}
