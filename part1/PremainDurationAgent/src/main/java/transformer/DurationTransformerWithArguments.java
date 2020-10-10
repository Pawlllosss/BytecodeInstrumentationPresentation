package transformer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//based on https://javapapers.com/core-java/java-instrumentation/
public class DurationTransformerWithArguments implements ClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(DurationTransformerWithArguments.class);

    private Set<String> classNamesToTransform;

    public DurationTransformerWithArguments(String[] classNames) {
        this.classNamesToTransform = new HashSet<>(Arrays.asList(classNames));
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;

        if (classNamesToTransform.contains(className)) {
            logger.info("Instrumenting: {}", className);
            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod method : methods) {
                    method.addLocalVariable("startTime", CtClass.longType);
                    var methodName = method.getName();
                    var beforeMessage = String.format("Starting measuring of %s method", methodName);
                    method.insertBefore(String.format("logger.debug(\"%s\");", beforeMessage));
                    method.insertBefore("startTime = System.nanoTime();");
                    method.insertAfter("logger.debug(\"Execution Duration " + methodName
                            + " (nano sec): \"+ (System.nanoTime() - startTime) );");
                }
                byteCode = ctClass.toBytecode();
                ctClass.detach();
                logger.info("Instrumentation of {} complete", className);
            } catch (Exception exception) {
                logger.error("Exception during instrumentation: {}", exception.getMessage());
            }
        }
        return byteCode;

    }
}
