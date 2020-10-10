import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transformer.AnnotationTransformer;
import transformer.MatterDTOConverterTransformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class InstrumentationAgent {

    private static final String MATTER_DTO_CONVERTER_CLASS_NAME = "pl.oczadly.example.webapp.matter.boundary.dto.MatterDTOConverter";
    private static final Logger logger = LoggerFactory.getLogger(InstrumentationAgent.class);

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        Set<String> classNamesToInstrument = getClassNames(agentArgs);
        logger.info("Executing agentmain with args: {}", agentArgs);

        for(Class<?> clazz: instrumentation.getAllLoadedClasses()) {
            // Annotated method duration measure
            if (shouldBeInstrumented(classNamesToInstrument, clazz)) {
                ClassLoader targetClassLoader = clazz.getClassLoader();
                ClassFileTransformer annotationTransformer = new AnnotationTransformer(clazz.getName(), targetClassLoader);
                performInstrumentation(instrumentation, clazz, annotationTransformer);
            }

            // Modifying toDTO method in MatterDTOConverter class
//            if (clazz.getName().equals(MATTER_DTO_CONVERTER_CLASS_NAME)) {
//                ClassLoader targetClassLoader = clazz.getClassLoader();
//                ClassFileTransformer matterDTOConverterTransformer = new MatterDTOConverterTransformer(targetClassLoader);
//                performInstrumentation(instrumentation, clazz, matterDTOConverterTransformer);
//            }
        }
    }

    private static Set<String> getClassNames(String agentArgs) {
        String[] classNames = agentArgs.split(Pattern.quote("+"));
        return new HashSet<>(Arrays.asList(classNames));
    }

    private static boolean shouldBeInstrumented(Set<String> classNamesToInstrument, Class<?> clazz) {
        return classNamesToInstrument.contains(clazz.getName());
    }

    private static void performInstrumentation(Instrumentation instrumentation, Class<?> clazz, ClassFileTransformer classFileTransformer) {
        instrumentation.addTransformer(classFileTransformer, true);

        try {
            instrumentation.retransformClasses(clazz);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to transform [" + clazz.getName() + "]", ex);
        } finally {
            instrumentation.removeTransformer(classFileTransformer);
        }
    }
}
