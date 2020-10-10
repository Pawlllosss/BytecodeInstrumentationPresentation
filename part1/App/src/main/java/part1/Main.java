package part1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static SamplePrinter samplePrinter = new SamplePrinter();
    public static AdvancedPrinter advancedPrinter = new AdvancedPrinter();

    public static void main(String[] args) throws InterruptedException {
        logger.info("Starting");
        delay(2000);
        samplePrinter.print1();
        samplePrinter.print2();
        delay(2000);
        advancedPrinter.printArgument("My amazing argument");
    }

    private static void delay(long milis) throws InterruptedException {
        logger.info("Thread is going to sleep");
        Thread.sleep(milis);
    }
}
