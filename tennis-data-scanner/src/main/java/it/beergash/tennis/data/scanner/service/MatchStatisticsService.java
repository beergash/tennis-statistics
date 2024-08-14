package it.beergash.tennis.data.scanner.service;

import it.beergash.data.common.repository.model.Match;
import it.beergash.tennis.data.scanner.model.LeastGamesNumberInSetPerPlayerRequest;
import it.beergash.tennis.data.scanner.model.MatchPerYearAndTournamentRequest;
import it.beergash.tennis.data.scanner.model.exceptions.TennisScannerException;
import it.beergash.tennis.data.scanner.service.reader.ISourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class MatchStatisticsService {

    @Autowired
    private TennisMatchStatistics tennisMatchStatistics;

    @Autowired
    @Qualifier("file")
    private ISourceReader sourceReader;

    public List<Match> getMatchesWithLeastGamesNumberPerSetAndPlayer(LeastGamesNumberInSetPerPlayerRequest request) throws Exception {
        List<Match> allMatches = sourceReader.readSourceInRangeYears(request.getStartYear(), request.getEndYear());
        return tennisMatchStatistics.getMatchesWithAtLeastSetGamesNumber(allMatches, request.getLeastGamesNumber());
    }

    public List<Match> getMatchesPerYearAndTournament(MatchPerYearAndTournamentRequest request) throws Exception {
        List<Match> allYearMatches = sourceReader.readSourceInRangeYears(request.getYear(), request.getYear());
        List<Match> result = tennisMatchStatistics.filterByTournament(allYearMatches, request.getTournament());
        if (CollectionUtils.isEmpty(result)) {
            throw new TennisScannerException(String.format("No found data per year %d and tournament %s", request.getYear(), request.getTournament()));
        }
        return result;
    }
}
