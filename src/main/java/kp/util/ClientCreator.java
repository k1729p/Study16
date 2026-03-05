package kp.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import kp.Application;
import kp.Constants;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Objects;


/**
 * The Elasticsearch client creator.
 */
public class ClientCreator {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * Private constructor to prevent instantiation.
     */
    private ClientCreator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates Elasticsearch client.
     *
     * @param apiKey the Elasticsearch API key
     * @return the Elasticsearch client
     */
    public static ElasticsearchClient createElasticsearchClient(String apiKey) {

        final ElasticsearchClient client = new ElasticsearchClient(createRestClientTransport(apiKey));
        logger.info("initializeElasticsearchClient(): server URL[{}]", Constants.SERVER_URL);
        return client;
    }

    /**
     * Creates the REST client transport.
     *
     * @return the REST client transport
     */
    private static RestClientTransport createRestClientTransport(String apiKey) {

        final SSLContext sslContext = createSSLContext();
        final RestClientBuilder restClientBuilder = RestClient.builder(HttpHost.create(Constants.SERVER_URL))
                .setHttpClientConfigCallback(builder -> builder.setSSLContext(sslContext))
                .setDefaultHeaders(Constants.DEFAULT_HEADER_FUN.apply(apiKey));
        return new RestClientTransport(restClientBuilder.build(), new JacksonJsonpMapper());
    }

    /**
     * Creates the SSL context.
     *
     * @return the SSL context
     */
    private static SSLContext createSSLContext() {

        try {
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, createTrustManagers(), new SecureRandom());
            return sslContext;
        } catch (GeneralSecurityException e) {
            logger.error("createSSLContext(): GeneralSecurityException[{}]", e.getMessage());
            System.exit(1);
        }
        return null;
    }

    /**
     * Creates the trust managers.
     *
     * @return the trust managers
     */
    private static TrustManager[] createTrustManagers() {

        try {
            final Certificate certificate;
            try (final InputStream inputStream =
                         Application.class.getClassLoader().getResourceAsStream("http_ca.crt")) {
                if (Objects.isNull(inputStream)) {
                    final String msg = "http_ca.crt not found in classpath";
                    logger.error("createTrustManagers(): {}", msg);
                    System.exit(1);
                }
                certificate = CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
            }
            final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("elastic-certificate", certificate);
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        } catch (IOException e) {
            logger.error("createTrustManagers(): IOException[{}]", e.getMessage());
            System.exit(1);
        } catch (GeneralSecurityException e) {
            logger.error("createTrustManagers(): GeneralSecurityException[{}]", e.getMessage());
            System.exit(1);
        }
        return new TrustManager[]{};
    }

    /**
     * Creates simple Elasticsearch client.
     *
     * @param apiKey the Elasticsearch API key
     * @return the Elasticsearch client
     */
    public static ElasticsearchClient createSimpleElasticsearchClient(String apiKey) {

        final ElasticsearchClient client =
                ElasticsearchClient.of(builder -> builder.host(Constants.SERVER_URL).apiKey(apiKey));
        logger.info("initializeSimpleElasticsearchClient():");
        return client;
    }
}
