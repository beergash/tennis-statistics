package it.beergash.tennis.data.scanner.service;

import it.beergash.data.common.repository.model.Match;
import it.beergash.data.common.repository.model.enums.RoundEnum;
import it.beergash.tennis.data.scanner.utils.MatchUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TennisMatchStatistics {

    private static final Logger log = LoggerFactory.getLogger(TennisMatchStatistics.class);

    /**
     * Get per each nationality number of titles
     * @param data
     * @param nationalityWinner
     * @return
     */
    public Map<String, Long> getTitlesNumberByNationality(List<Match> data, String nationalityWinner) {
        return data.stream().filter(d -> RoundEnum.FINAL.value().equalsIgnoreCase(d.getRound()))
                .filter(d -> nationalityWinner != null ? d.getNationalityPlayerWinner().equalsIgnoreCase(nationalityWinner) : true)
                .collect(Collectors.groupingBy(Match::getPlayerWinner, Collectors.counting()));
    }

    /**
     * get per each country number of titles
     * @param data
     * @return
     */
    public Map<String, Long> getTitlesByCountry(List<Match> data) {
        return aggregateAndCountMatchesByParameters(data, Match::getNationalityPlayerWinner);
    }

    /**
     * get per each player, number of titles
     * @param data
     * @return
     */
    public Map<String, Long> getTitlesByPlayer(List<Match> data) {
        return aggregateAndCountMatchesByParameters(data, Match::getPlayerWinner);
    }


    private Map<String, Long> aggregateAndCountMatchesByParameters(List<Match> data, Function<Match, String> aggregationParameter) {
        return data.stream()
                .filter(d -> RoundEnum.FINAL.value().equalsIgnoreCase(d.getRound()))
                .filter(d -> !StringUtils.isEmpty(d.getNationalityPlayerWinner()))
                .collect(Collectors.groupingBy(aggregationParameter, Collectors.counting()))
                .entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().intValue() - o1.getValue().intValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1, LinkedHashMap::new));

    }

    public List<Match> getTournamentWinners(List<Match> data, String tournament) {
        return data.stream().filter(d -> RoundEnum.FINAL.value().equalsIgnoreCase(d.getRound()))
                .filter(d -> tournament.equalsIgnoreCase(d.getTournament()))
                .sorted((o1, o2) -> Integer.valueOf(o2.getYear()) - Integer.valueOf(o1.getYear()))
                .collect(Collectors.toList());
    }

    public Match getMatchMostGames(List<Match> data) {
        return data.stream()
                .max(Comparator.comparingInt(o -> MatchUtils.getGamesNumber(o.getScore()))).get();
    }

    public List<Match> getMatchesWithAtLeastSetGamesNumber(List<Match> data, int minGamesNumber) {
        return data.stream()
                .filter(d -> MatchUtils.getGamesList(d.getScore()).stream().anyMatch(g -> g >= minGamesNumber))
                .collect(Collectors.toList());
    }

    public List<Match> filterByTournament(List<Match> data, String tournament) {
        return data.stream().filter(d -> tournament.equalsIgnoreCase(d.getTournament()))
                .filter(d -> tournament.equalsIgnoreCase(d.getTournament()))
                .sorted(Comparator.comparingInt(o -> Integer.valueOf(RoundEnum.valueOfString(o.getRound()).getStage())))
                .collect(Collectors.toList());
    }

}
