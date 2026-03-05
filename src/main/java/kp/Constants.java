package kp;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.io.File;
import java.util.function.Function;

/**
 * The constants.
 */
@SuppressWarnings("doclint:missing")
public final class Constants {
    /**
     * The sleep time after the dataset import.
     */
    public static final int WAIT_AFTER_DATASET_IMPORT = 5;
    /**
     * The server URL.
     */
    public static final String SERVER_URL = "https://localhost:9200";
    /**
     * The default header function.
     */
    public static final Function<String, Header[]> DEFAULT_HEADER_FUN = apiKey -> new Header[]{
            new BasicHeader("Authorization", "ApiKey " + apiKey)
    };
    /**
     * The dataset file created in Solr project 'Study15'.
     */
    public static final File DATASET_FILE = new File("../Study15/solr-requests/dataset.json");
    /**
     * The index name.
     */
    public static final String INDEX_NAME = "kp_study";
    /**
     * The keyword.
     */
    public static final String KEYWORD = "reactive";
    /**
     * The project.
     */
    public static final String PROJECT = "miscellany";
    /**
     * The "Must Appear" query.
     */
    public static final Query MUST_APPEAR_QUERY = new Query.Builder().term(
                    termBuilder -> termBuilder.field("keyword").value(
                            valueBuilder -> valueBuilder.stringValue(KEYWORD)))
            .build();
    /**
     * The "Must Not Appear" query.
     */
    public static final Query MUST_NOT_APPEAR_QUERY = new Query.Builder().term(
                    termBuilder -> termBuilder.field("project").value(
                            valueBuilder -> valueBuilder.stringValue(PROJECT)))
            .build();

    /**
     * Private constructor to prevent instantiation.
     */
    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
