package it.beergash.tennis.data.scanner.utils;

import it.beergash.reader.writer.api.reader.model.FileFeature;
import it.beergash.reader.writer.api.reader.model.FileSheet;
import it.beergash.reader.writer.api.reader.model.FileTrace;
import it.beergash.data.common.repository.model.Match;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class FileFeatureBuilder {

    Map<String, String> correctedTournamentNames = new HashMap<>();

    @PostConstruct
    public void init() {
        correctedTournamentNames.put("FO - RG", "Roland Garros");
    }

    public FileFeature createCsvMatchesFeatures() {
        FileFeature ff = new FileFeature();
        ff.setHasHeader(false);
        FileSheet sheet = new FileSheet();
        sheet.setName("matches");
        sheet.setSeparator(";");
        sheet.setTargetClass(Match.class);
        FileTrace f1 = new FileTrace("1", "year");
        FileTrace f2 = new FileTrace("2", "tournament");
        f2.setInterpreter(s -> {
            Optional<String> wrongTrnm = correctedTournamentNames.entrySet().stream()
                    .filter(cs -> s != null && s.equalsIgnoreCase(cs.getKey()))
                    .map(Map.Entry::getKey).findFirst();
            return wrongTrnm.map(string -> s.replace(string, correctedTournamentNames.get(string))).orElse(s);
        });
        FileTrace f3 = new FileTrace("3", "surface");
        FileTrace f4 = new FileTrace("4", "playerWinner");
        FileTrace f5 = new FileTrace("5", "playerLoser");
        FileTrace f6 = new FileTrace("6", "nationalityPlayerWinner");
        FileTrace f7 = new FileTrace("7", "nationalityPlayerLoser");
        FileTrace f8 = new FileTrace("8", "score");
        FileTrace f9 = new FileTrace("9", "round");
        FileTrace f10 = new FileTrace("10", "tournamentLevel");
        sheet.setFields(Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10));
        ff.setSheets(Arrays.asList(sheet));
        return ff;
    }
}
