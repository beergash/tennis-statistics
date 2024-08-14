package it.beergash.tennis.data.scanner.service;

import it.beergash.data.common.repository.model.Match;
import it.beergash.data.common.repository.model.enums.RoundEnum;
import it.beergash.data.common.repository.model.enums.TournamentLevel;
import it.beergash.tennis.data.scanner.model.LeastGamesNumberInSetPerPlayerRequest;
import it.beergash.tennis.data.scanner.model.MatchPerYearAndTournamentRequest;
import it.beergash.tennis.data.scanner.model.exceptions.TennisScannerException;
import it.beergash.tennis.data.scanner.service.reader.ISourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MatchStatisticsService {

    @Autowired
    private DomainData domainData;

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

    public Map<String, Long> getPlayerListedByTournamentLevelWon(TournamentLevel level) {
        List<Match> allMatches = domainData.getAllMatches();
        return allMatches.stream().filter(m -> m.getTournamentLevel() != null && m.getTournamentLevel().equals(level.getValue()))
                .filter(m -> RoundEnum.FINAL.value().equals(m.getRound()))
                .map(Match::getPlayerWinner)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }
}
