package transformer;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

//based on https://javapapers.com/core-java/java-instrumentation/ and https://www.infoq.com/articles/Living-Matrix-Bytecode-Manipulation/
public class AnnotationTransformer implements ClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationTransformer.class);
    public static final String ANNOTATION_TYPE_NAME = "part2.DetailedLogs";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        byte[] byteCode = classfileBuffer;

        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get(className.replace("/", "."));

            if (ctClass.hasAnnotation(ANNOTATION_TYPE_NAME)) {
                byteCode = instrumentClass(ctClass, className);
            }
        } catch (Exception exception) {
            logger.error("Exception during instrumentation: {}", exception.getMessage());
        }

        return byteCode;
    }

    private byte[] instrumentClass(CtClass ctClass, String className) throws CannotCompileException, IOException {
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
        byte[] bytes = ctClass.toBytecode();
        ctClass.detach();
        logger.info("Instrumentation of {} complete", className);

        return bytes;
    }
}
