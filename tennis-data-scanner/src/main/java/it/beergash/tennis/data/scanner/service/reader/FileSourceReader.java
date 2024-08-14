package it.beergash.tennis.data.scanner.service.reader;

import it.beergash.data.common.repository.model.Match;
import it.beergash.reader.writer.api.exception.FileReaderException;
import it.beergash.reader.writer.api.model.FileType;
import it.beergash.reader.writer.api.reader.interfaces.IFileReader;
import it.beergash.reader.writer.api.reader.model.FileFeature;
import it.beergash.reader.writer.api.reader.model.FileResult;
import it.beergash.tennis.data.scanner.utils.FileFeatureBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@Qualifier("file")
public class FileSourceReader implements ISourceReader {

    public static final String INPUT_ATP_MATCHES_S_CSV = "/input/atp_matches_%s.csv";
    public static final int START_YEAR = 1968;

    @Autowired
    @Qualifier(FileType.CSV)
    private IFileReader fileReader;

    @Autowired
    private FileFeatureBuilder fileFeatureBuilder;
    public List<Match> readSourceInRangeYears() throws Exception {
        int currentYear = LocalDate.now().getYear();
        return readSourceInRangeYears(START_YEAR, currentYear);
    }

    @Override
    public List<Match> readSourceInRangeYears(int startYear, int endYear) throws Exception {
        List<Match> data = new ArrayList<>();
        List<String> sourceFiles = new ArrayList<>();
        IntStream.rangeClosed(startYear, endYear).forEach(i ->
                sourceFiles.add(String.format(INPUT_ATP_MATCHES_S_CSV, i)));
        for (String f : sourceFiles) {
            try (InputStream is = this.getClass().getResourceAsStream(f)) {
                data.addAll(readAtpCsv(is));
            }
        }
        return data;
    }

    private List<Match> readAtpCsv(InputStream is) throws IOException, FileReaderException {
        FileFeature ff = fileFeatureBuilder.createCsvMatchesFeatures();
        Map<String, FileResult> result = fileReader.readFile(is, ff);
        FileResult fr = result.entrySet().stream().iterator().next().getValue();
        List<Match> matches = (List<Match>) fr.getMappedData();
        return matches;
    }
}
