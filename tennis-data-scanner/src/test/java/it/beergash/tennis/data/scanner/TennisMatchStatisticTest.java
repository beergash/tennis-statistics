package it.beergash.tennis.data.scanner;

import it.beergash.data.common.repository.model.Match;
import it.beergash.tennis.data.scanner.service.TennisMatchStatistics;
import it.beergash.tennis.data.scanner.service.reader.ISourceReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TennisMatchStatisticTest {

    @Autowired
    @Qualifier("file")
    private ISourceReader sourceReader;

    @Autowired
    private TennisMatchStatistics tennisMatchStatistics;

    @Test
    public void testTitlesByPlayer() throws Exception {
        List<Match> data = sourceReader.readSourceInRangeYears(2006, 2006);
        Map<String, Long> result = tennisMatchStatistics.getTitlesByPlayer(data);
        result.entrySet().stream()
                .forEach(r -> System.out.printf("%s - %d \n", r.getKey(), r.getValue()));
    }

    @Test
    public void testTitlesByCountry() throws Exception {
        List<Match> data = sourceReader.readSourceInRangeYears(2021, 2021);
        Map<String, Long> result = tennisMatchStatistics.getTitlesByCountry(data);
        result.entrySet().stream()
                .forEach(r -> System.out.printf("%s - %d \n", r.getKey(), r.getValue()));
    }
}
