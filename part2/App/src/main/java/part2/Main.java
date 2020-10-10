package part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static SamplePrinter samplePrinter = new SamplePrinter();
    public static AdvancedPrinter advancedPrinter = new AdvancedPrinter();

    public static void main(String[] args) {
        logger.info("Starting");
        samplePrinter.print1();
        samplePrinter.print2();
        advancedPrinter.printArgument("My amazing argument");
    }
}
