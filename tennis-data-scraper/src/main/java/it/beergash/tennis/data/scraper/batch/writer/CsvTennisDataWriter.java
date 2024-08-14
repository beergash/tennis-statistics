package it.beergash.tennis.data.scraper.batch.writer;

import it.beergash.reader.writer.api.model.FileType;
import it.beergash.reader.writer.api.writer.interfaces.IReportWriter;
import it.beergash.reader.writer.api.writer.model.ReportFeature;
import it.beergash.reader.writer.api.writer.model.ReportSheet;
import it.beergash.reader.writer.api.writer.model.ReportTrace;
import it.beergash.data.common.repository.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@StepScope
public class CsvTennisDataWriter implements ItemWriter<List<Match>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvTennisDataWriter.class);

    @Value("${year}")
    private String year;

    @Value("${dir.target.files}")
    private String dirTarget;

    @Value("${prefix.output.matches.filename}")
    private String prefixOutputMatchesFilename;

    @Autowired
    @Qualifier(FileType.CSV)
    private IReportWriter reportWriter;

    private ReportFeature reportFeature;

    @PostConstruct
    public void init() {
        File dir = new File(dirTarget);
        Arrays.stream(Objects.requireNonNull(dir.listFiles())).forEach(File::delete);
    }

    @Override
    public void write(List<? extends List<Match>> list) throws Exception {
        LOGGER.debug("enter into Csv item writer");
        if (reportFeature == null) {
            reportFeature = createReportFeature();
        } else {
            reportFeature.setAppendMode(true);
        }
        List<Match> data = CollectionUtils.isEmpty(list) ? null : list.get(0);
        if (!CollectionUtils.isEmpty(data)) {
            String year = data.get(0).getYear();
            reportFeature.setFilename(String.format(prefixOutputMatchesFilename + "%s.csv",year));
            reportFeature.getSheets().get(0).setTypedData(data);
            reportWriter.writeReport(reportFeature);
        }
    }

    private ReportFeature createReportFeature() {
        ReportFeature rf = new ReportFeature();
        rf.setOutputDirectory(dirTarget);
        ReportSheet sheet = new ReportSheet();
        sheet.setTypedDataClass(Match.class);
        sheet.setSeparator(";");
        List<ReportTrace> trace = new ArrayList<>();
        trace.add(new ReportTrace(1, "year"));
        trace.add(new ReportTrace(2, "tournament"));
        trace.add(new ReportTrace(3, "surface"));
        trace.add(new ReportTrace(4, "playerWinner"));
        trace.add(new ReportTrace(5, "playerLoser"));
        trace.add(new ReportTrace(6, "nationalityPlayerWinner"));
        trace.add(new ReportTrace(7, "nationalityPlayerLoser"));
        trace.add(new ReportTrace(8, "score"));
        trace.add(new ReportTrace(9, "round"));
        trace.add(new ReportTrace(10, "tournamentLevel"));
        sheet.setTrace(trace);
        List<ReportSheet> sheets = Arrays.asList(sheet);
        rf.setSheets(sheets);
        return rf;
    }
}
