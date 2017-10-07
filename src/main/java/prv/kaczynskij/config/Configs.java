package prv.kaczynskij.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;

public class Configs {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configs.class);

    private static final Config systemProperties = ConfigFactory.systemProperties();
    private static final Config appProperties = new Builder().withDefaultAppResources().build();

    public static Config systemProperties() {
	return systemProperties;
    }

    public static Config appProperties() {
	return appProperties;
    }

    public static class Builder {

	private static final String APPLICATION_CONF_FILE_PATTERN = "application.%s.conf";
	private static final String DEFAULT_CONF_FILE = "application.conf";
	private final List<String> configs;

	public Builder() {
	    this.configs = Lists.newLinkedList();
	}

	public Builder withResource(String resource) {
	    configs.add(resource);
	    return this;
	}

	public Builder withDefaultAppResources() {
	    String env = systemProperties.hasPath("omnix.env") ? systemProperties.getString("omnix.env") : "local";
	    String envFile = String.format(APPLICATION_CONF_FILE_PATTERN, env);
	    configs.add(envFile);
	    configs.add(DEFAULT_CONF_FILE);
	    return this;
	}

	public Config build() {
	    LOGGER.info("Loading config properties...");
	    configs.forEach(LOGGER::info);
	    Preconditions.checkArgument(configs.size() > 0, "No configs file specified!");
	    Config appConfig = ConfigFactory.parseResources(configs.remove(0));
	    for (String resource : configs) {
		appConfig = appConfig.withFallback(ConfigFactory.parseResources(resource));
	    }

	    appConfig = appConfig.resolve();

	    LOGGER.debug("Logging properties:");
	    LOGGER.debug(appConfig.root().render(ConfigRenderOptions.concise().setFormatted(true)));

	    return appConfig;
	}

    }

}
