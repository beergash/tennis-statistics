package it.beergash.tennis.data.scraper.batch.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import it.beergash.data.common.repository.model.Match;
import it.beergash.tennis.data.scraper.model.exceptions.MongoDbWriterException;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@StepScope
public class MongoDBDataWriter implements ItemWriter<List<Match>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBDataWriter.class);

    @Value("${mongo.host}")
    private String mongoHost;

    @Value("${mongo.port}")
    private int mongoPort;

    @Value("${mongo.db.name}")
    private String mongoDatabase;

    @Value("${mongo.atp.collection}")
    private String mongoAtpCollection;

    private MongoClient mongoClient;

    private CodecRegistry pojoCodecRegistry;

    @PostConstruct
    public void init() {
        pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(String.format("mongodb://%s:%s", mongoHost, mongoPort)))
                .build();
        mongoClient = MongoClients.create(settings);
    }

    @Override
    public void write(List<? extends List<Match>> list) throws Exception {
        LOGGER.debug("enter into mongodb item writer");
        List<Match> data = CollectionUtils.isEmpty(list) ? null : list.get(0);
        if (!CollectionUtils.isEmpty(data)) {
            String year = data.get(0).getYear();
            String tournament = data.get(0).getTournament();
            MongoDatabase db = mongoClient.getDatabase(mongoDatabase);
            MongoCollection<Document> collection = db.getCollection(mongoAtpCollection);
            deleteCollectionByYearAndTournament(collection, year, tournament);
            ObjectMapper om = new ObjectMapper();
            List<Document> documents = data.stream()
                    .map(m -> {
                        String jsonMatch;
                        try {
                            jsonMatch = om.writeValueAsString(m);
                        } catch (JsonProcessingException e) {
                            throw new MongoDbWriterException(String.format("Can't convert match to json: %s", m.toString()));
                        }
                        Document document = Document.parse(jsonMatch);
                        return document;
                    }).collect(Collectors.toList());
            collection.insertMany(documents);
        }
    }

    private void deleteCollectionByYearAndTournament(MongoCollection collection, String year, String tournament) {
        collection.deleteMany(Filters.and(Filters.eq("year", year), Filters.eq("tournament", tournament)));
    }
}
