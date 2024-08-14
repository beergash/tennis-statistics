package it.beergash.tennis.data.scanner.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.beergash.api.common.annotations.RequestLogger;
import it.beergash.data.common.repository.model.Match;
import it.beergash.tennis.data.scanner.model.LeastGamesNumberInSetPerPlayerRequest;
import it.beergash.tennis.data.scanner.model.MatchPerYearAndTournamentRequest;
import it.beergash.tennis.data.scanner.service.MatchStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "MatchStatisticsController")
public class MatchStatisticsController {

    @Autowired
    private MatchStatisticsService statisticsService;

    @PostMapping(value= "/least-games-set-player", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "All matches in which a player has got at least a games number in the same set greater or equal to the one indicated in the request", response = List.class)
    @RequestLogger
    public List<Match> getMatchesWithLeastGamesNumberPerSetAndPlayer(@RequestBody LeastGamesNumberInSetPerPlayerRequest request) throws Exception {
        return statisticsService.getMatchesWithLeastGamesNumberPerSetAndPlayer(request);
    }

    @PostMapping(value= "/matches-year-tournament", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "All matches by tournament", response = List.class)
    @RequestLogger
    public List<Match> getMatchesPerYearAndTournament(@RequestBody MatchPerYearAndTournamentRequest request) throws Exception {
        return statisticsService.getMatchesPerYearAndTournament(request);
    }

}
