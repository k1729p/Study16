package kp;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import kp.service.KpService;

/**
 * The application for the elastic search client.
 * <p>
 * <a href=
 * "https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html">Elasticsearch
 * Java API Client</a>
 */
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	/**
	 * The constructor.
	 */
	public Application() {
		super();
	}

	/**
	 * The entry point for the application.
	 * 
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {

		final String password = Optional.ofNullable(args).filter(arg -> arg.length > 0).map(arg -> arg[0]).orElse("");
		if (password.isBlank()) {
			logger.error("main(): password is required!");
			System.exit(1);
		}
		final KpService kpService = new KpService(password);
		try {
			kpService.recreateIndex();
			kpService.addBoxesFromFile();
			waitAfterDatasetImport();
			kpService.searchWithQuery();
		} catch (IOException | ElasticsearchException e) {
			logger.error(String.format("main(): exception[%s]", e.getMessage()));
			System.exit(1);
		}
		logger.info("main():");
		System.exit(0);
	}

	/**
	 * Waits after dataset import.
	 * 
	 */
	private static void waitAfterDatasetImport() {

		try {
			TimeUnit.SECONDS.sleep(Constants.WAIT_AFTER_DATASET_IMPORT);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.error(String.format("waitAfterDatasetImport(): exception[%s]", e.getMessage()));
		}
	}

}