package part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DetailedLogs
public class AdvancedPrinter {

    private static final Logger logger = LoggerFactory.getLogger(AdvancedPrinter.class);

    public void printArgument(String argument) {
        logger.info("Going to print argument: {}", argument);
    }
}
