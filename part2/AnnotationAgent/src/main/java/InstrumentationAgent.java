import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transformer.AnnotationTransformer;

import java.lang.instrument.Instrumentation;

public class InstrumentationAgent {

    private static final Logger logger = LoggerFactory.getLogger(InstrumentationAgent.class);

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        logger.info("Executing premain!");
        instrumentation.addTransformer(new AnnotationTransformer());
    }
}
