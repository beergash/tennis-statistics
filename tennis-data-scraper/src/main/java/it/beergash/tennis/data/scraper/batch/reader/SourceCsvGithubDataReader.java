package it.beergash.tennis.data.scraper.batch.reader;

import it.beergash.reader.writer.api.model.FileType;
import it.beergash.reader.writer.api.reader.interfaces.IFileReader;
import it.beergash.reader.writer.api.reader.model.FileFeature;
import it.beergash.reader.writer.api.reader.model.FileResult;
import it.beergash.reader.writer.api.reader.model.FileSheet;
import it.beergash.reader.writer.api.reader.model.FileTrace;
import it.beergash.reader.writer.api.writer.interfaces.ICellValueInterpreter;
import it.beergash.data.common.repository.model.Match;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.Year;
import java.util.*;

@Component
@StepScope
public class SourceCsvGithubDataReader implements ItemReader<List<Match>> {

    public static final String UNKOKNW = "UNK";
    public static final int START_YEAR = 1968;
    private int currentYear = START_YEAR;

    @Autowired
    @Qualifier(FileType.CSV)
    private IFileReader fileReader;

    @Value("${dir.source.files}")
    private String dirSourceFiles;

    private Map<String, String> correctScores = new HashMap<>();

    @PostConstruct
    public void init() {
        correctScores.put("1-210", "12-10");
        correctScores.put("1-311", "13-11");
        correctScores.put("1-412", "14-12");
        correctScores.put("1-513", "15-13");
        correctScores.put("1-614", "16-14");
        correctScores.put("1-715", "17-15");
        correctScores.put("1-816", "18-16");
        correctScores.put("1-917", "19-17");
        correctScores.put("64-0", "6-4");
    }

    @Override
    public List<Match> read() throws Exception {
        FileFeature ff = buildSourceCsvFeatures();
        List<Match> data = null;
        final int endYear = Year.now().getValue();
        if (currentYear <= endYear) {
            File file = new File(String.format(dirSourceFiles + "/atp_matches_%s.csv", currentYear));
            Map<String, FileResult> result = fileReader.readFile(file, ff);
            FileResult fr = result.entrySet().stream().iterator().next().getValue();
            data = (List<Match>) fr.getMappedData();
            currentYear++;
        }
        return data;
    }

    private FileFeature buildSourceCsvFeatures() {
        ICellValueInterpreter unknownNationality = s -> UNKOKNW.equalsIgnoreCase(s) ? null : s;
        FileFeature ff = new FileFeature();
        ff.setHasHeader(true);
        FileSheet sheet = new FileSheet();
        sheet.setName("matches");
        sheet.setHasHeader(true);
        sheet.setSeparator(",");
        sheet.setTargetClass(Match.class);
        FileTrace f1 = new FileTrace();
        f1.setFieldJavaAttribute("tournament");
        f1.setPosition("2");
        FileTrace f2 = new FileTrace();
        f2.setFieldJavaAttribute("playerWinner");
        f2.setPosition("11");
        FileTrace f3 = new FileTrace();
        f3.setFieldJavaAttribute("score");
        f3.setPosition("24");
        f3.setInterpreter(s -> {
            Optional<String> wrongScore = correctScores.entrySet().stream()
                    .filter(cs -> StringUtils.containsIgnoreCase(s, cs.getKey()))
                    .map(cs -> cs.getKey()).findFirst();
            return wrongScore.isPresent() && s != null ? s.replace(wrongScore.get(), correctScores.get(wrongScore.get())) : s;
        });
        FileTrace f4 = new FileTrace();
        f4.setFieldJavaAttribute("playerLoser");
        f4.setPosition("19");
        FileTrace f5 = new FileTrace();
        f5.setFieldJavaAttribute("round");
        f5.setPosition("26");
        FileTrace f6 = new FileTrace();
        f6.setFieldJavaAttribute("year");
        f6.setPosition("1");
        f6.setInterpreter(s -> s.split("-")[0]);
        FileTrace f7 = new FileTrace();
        f7.setFieldJavaAttribute("nationalityPlayerWinner");
        f7.setPosition("14");
        f7.setInterpreter(unknownNationality);
        FileTrace f8 = new FileTrace();
        f8.setFieldJavaAttribute("nationalityPlayerLoser");
        f8.setPosition("22");
        f8.setInterpreter(unknownNationality);
        FileTrace f9 = new FileTrace();
        f9.setFieldJavaAttribute("surface");
        f9.setPosition("3");
        List<FileTrace> fields = Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8, f9);
        sheet.setFields(fields);
        List<FileSheet> sheets = Arrays.asList(sheet);
        ff.setSheets(sheets);
        return ff;
    }
}
