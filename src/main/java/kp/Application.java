package kp;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import kp.model.Box;
import kp.util.ClientCreator;
import kp.util.DatasetManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The application for the Elasticsearch client.
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    /*
     The simple client builder fails, because certificate is not trusted!
     Exceptions stack:
     |  javax.net.ssl.SSLHandshakeException:
     |    (certificate_unknown) PKIX path building failed:
     |      sun.security.provider.cert path.SunCertPathBuilderException:
     |        unable to find valid certification path to requested target
     */
    private static final boolean USE_LOW_LEVEL_REST_CLIENT = true;

    /**
     * The primary entry point for launching the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        final String apiKey = Optional.ofNullable(args).filter(arg -> arg.length > 0)
                .map(arg -> arg[0]).orElse("");
        if (apiKey.isBlank()) {
            logger.error("main(): API key is required! Exiting");
            System.exit(1);
        }
        final ElasticsearchClient elasticsearchClient;
        if (USE_LOW_LEVEL_REST_CLIENT) {
            elasticsearchClient = ClientCreator.createElasticsearchClient(apiKey);
        } else {
            elasticsearchClient = ClientCreator.createSimpleElasticsearchClient(apiKey);
        }
        DatasetManager.recreateIndex(elasticsearchClient);
        DatasetManager.addBoxesFromFile(elasticsearchClient);
        DatasetManager.waitAfterDatasetImport();
        searchWithQuery(elasticsearchClient);
        logger.info("main():");
        System.exit(0);
    }

    /**
     * Searches with the query.
     *
     * @param elasticsearchClient the Elasticsearch client
     */
    private static void searchWithQuery(ElasticsearchClient elasticsearchClient) {

        SearchResponse<Box> searchResponse = null;
        try {
            searchResponse = elasticsearchClient.search(
                    searchRequestBuilder -> searchRequestBuilder.size(100)
                            .index(Constants.INDEX_NAME)
                            .query(queryBuilder -> queryBuilder.bool(
                                    boolQueryBuilder -> boolQueryBuilder
                                            .must(Constants.MUST_APPEAR_QUERY)
                                            .mustNot(Constants.MUST_NOT_APPEAR_QUERY))),
                    Box.class);
        } catch (IOException e) {
            logger.error("searchWithQuery(): IOException[{}]", e.getMessage());
            System.exit(1);
        }
        if (Objects.isNull(searchResponse)) {
            logger.error("searchWithQuery(): searchResponse is null");
            System.exit(1);
        }
        final List<Hit<Box>> hitList = searchResponse.hits().hits();
        if (hitList.isEmpty()) {
            logger.warn("searchWithQuery(): nothing found with keyword[{}]", Constants.KEYWORD);
            return;
        }
        final TotalHits total = searchResponse.hits().total();
        final StringBuilder strBld = new StringBuilder();
        strBld.append("searchWithQuery(): keyword[%s], not in project[%s]%n".formatted(
                Constants.KEYWORD, Constants.PROJECT));
        if (Objects.nonNull(total)) {
            strBld.append("\tresults total[%s], total is accurate[%b]%n"
                    .formatted(total.value(), TotalHitsRelation.Eq == total.relation()));
        } else {
            strBld.append("\t'TotalHits' is null%n");
        }
        final AtomicInteger atomic = new AtomicInteger();
        hitList.forEach(hit -> {
            final Box box = hit.source();
            if (Objects.nonNull(box)) {
                strBld.append(("%n\t%02d, score[%.3f], keyword[%s], project[%s], category[%s]%n" +
                               "\t\tpath[%s]%n\t\tfileName[%s], fileExtension[%s], lineNumber[%d]").formatted(
                        atomic.incrementAndGet(), hit.score(), box.keyword(), box.project(), box.category(),
                        box.path(), box.fileName(), box.fileExtension(), box.lineNumber()));
            } else {
                strBld.append("\tbox is null%n");
            }
        });
        if (logger.isInfoEnabled()) {
            logger.info(strBld.toString());
        }
    }

}