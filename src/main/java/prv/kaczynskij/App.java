package prv.kaczynskij;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import prv.kaczynskij.config.Configs;

public class App {

    private final static Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
	LOGGER.info("Omnix - starting...");
	LOGGER.info("Message: {}", returnHelloWorld());
	Configs.appProperties();
	LOGGER.info("Omnix - ending...");
    }

    public static String returnHelloWorld() {
	return "Hello World!";
    }

}
