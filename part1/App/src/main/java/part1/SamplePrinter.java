package part1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamplePrinter {

    private static final Logger logger = LoggerFactory.getLogger(SamplePrinter.class);

    public void print1() {
        logger.info("Hello guys");
    }

    public void print2() {
        logger.info("Another sample print");
    }
}
