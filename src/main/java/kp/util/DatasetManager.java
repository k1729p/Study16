package kp.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import kp.Constants;
import kp.model.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The dataset manager.
 */
public class DatasetManager {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * Private constructor to prevent instantiation.
     */
    private DatasetManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Recreates the index.
     *
     * @param elasticsearchClient the Elasticsearch client
     */
    public static void recreateIndex(ElasticsearchClient elasticsearchClient) {

        try {
            elasticsearchClient.indices().delete(builder -> builder.index(Constants.INDEX_NAME));
        } catch (ElasticsearchException | IOException e) {
            logger.warn("recreateIndex(): exception {}", e.getMessage());
        }
        try {
            elasticsearchClient.indices().create(builder -> builder.index(Constants.INDEX_NAME));
        } catch (ElasticsearchException | IOException e) {
            logger.error("recreateIndex(): exception {}", e.getMessage());
            System.exit(1);
        }
        logger.info("recreateIndex(): index name[{}]", Constants.INDEX_NAME);
    }

    /**
     * Adds the boxes from the dataset file.
     *
     * @param elasticsearchClient the Elasticsearch client
     */
    public static void addBoxesFromFile(ElasticsearchClient elasticsearchClient) {

        final BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();
        int itemsNumber = 0;
        try {
            final List<Box> boxList = new ObjectMapper().readValue(Constants.DATASET_FILE, new TypeReference<>() {
            });
            boxList.forEach(box -> bulkRequestBuilder.operations(
                    operBuilder -> operBuilder.index(
                            idxBuilder -> idxBuilder.index(Constants.INDEX_NAME)
                                    .id(box.id()).document(box))));
            final BulkResponse response = elasticsearchClient.bulk(bulkRequestBuilder.build());
            if (response.errors()) {
                for (BulkResponseItem item : response.items()) {
                    if (item.error() != null) {
                        logger.error("addBoxesFromFile(): item error[{}]", item.error().reason());
                    }
                }
                return;
            }
            itemsNumber = response.items().size();
        } catch (ElasticsearchException | IOException e) {
            logger.error("addBoxesFromFile(): exception[{}]", e.getMessage());
            System.exit(1);
        }
        logger.info("addBoxesFromFile(): file[{}], number of items[{}]",
                Constants.DATASET_FILE, itemsNumber);
    }

    /**
     * Waits after dataset import.
     */
    public static void waitAfterDatasetImport() {

        try {
            TimeUnit.SECONDS.sleep(Constants.WAIT_AFTER_DATASET_IMPORT);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupt status
            logger.error("waitAfterDatasetImport(): exception[{}]", e.getMessage());
        }
    }

}
