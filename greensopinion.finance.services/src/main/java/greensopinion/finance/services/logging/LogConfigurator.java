package greensopinion.finance.services.logging;

import static java.text.MessageFormat.format;

import java.io.File;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.GreenGap;

public class LogConfigurator {
	private static final int MEGABYTE = 1024 * 1024;
	private static final int COUNT = 3;
	private static final int LIMIT = MEGABYTE / COUNT;

	private final Logger logger = AppLogging.logger();

	public void configure(File directory) {
		removeHandlers();
		addHandlers(directory);
		logStartup();
		setLogLevel();
	}

	protected void setLogLevel() {
		logger.setLevel(Level.INFO);
	}

	protected void logStartup() {
		logger.setLevel(Level.INFO);
		logger.info(format("{0} application startup", GreenGap.APP_NAME));
	}

	protected void addHandlers(File directory) {
		configureFileLogger(directory);
	}

	protected void removeHandlers() {
		for (Handler handler : ImmutableList.copyOf(Arrays.asList(logger.getHandlers()))) {
			logger.removeHandler(handler);
			if (handler instanceof FileHandler) {
				((FileHandler) handler).close();
			}
		}
	}

	private void configureFileLogger(File directory) {
		File logLocation = logLocation(directory);
		if (!logLocation.exists()) {
			logLocation.mkdirs();
		}
		try {
			FileHandler handler = new FileHandler(pathPattern(logLocation), LIMIT, COUNT, true);
			handler.setFormatter(fileFormatter());
			logger.addHandler(handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Formatter fileFormatter() {
		return new SimpleFormatter();
	}

	private File logLocation(File directory) {
		return new File(directory, "logs");
	}

	private String pathPattern(File logLocation) {
		return format("{0}/application.%g.log", logLocation.getAbsolutePath());
	}
}
