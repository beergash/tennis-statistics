package it.beergash.tennis.data.scanner.service.reader;

import it.beergash.data.common.repository.model.Match;

import java.util.List;

public interface ISourceReader {

    public List<Match> readSourceInRangeYears(int startYear, int endYear) throws Exception;
}
