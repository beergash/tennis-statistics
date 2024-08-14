package it.beergash.tennis.data.scanner.service;

import it.beergash.data.common.repository.model.Match;
import it.beergash.tennis.data.scanner.service.reader.ISourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author Andrea Aresta
 */
@Component
public class DomainData {

    @Autowired
    @Qualifier("file")
    private ISourceReader sourceReader;

    private List<Match> allMatches;

    @PostConstruct
    public void init() throws Exception {
        allMatches = sourceReader.readSourceInRangeYears();

    }

    public List<Match> getAllMatches() {
        return allMatches;
    }
}
