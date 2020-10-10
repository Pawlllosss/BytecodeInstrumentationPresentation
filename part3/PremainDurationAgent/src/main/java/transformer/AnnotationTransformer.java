package transformer;

import javassist.ByteArrayClassPath;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationTransformer implements ClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationTransformer.class);
    private static final String ANNOTATION_NAME = "pl.oczadly.example.webapp.annotation.DetailedLogs";

    private String className;
    private ClassLoader classLoader;

    public AnnotationTransformer(String className, ClassLoader classLoader) {
        this.className = className.replace('.', '/');
        this.classLoader = classLoader;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;

        try {
            if (className.equals(this.className) && loader.equals(classLoader)) {
                logger.info("Class name equals and class loader equals");
                String binName = className.replace('/', '.');
                ClassPool classPool = new ClassPool(true);
                classPool.appendClassPath(new LoaderClassPath(classLoader));
                classPool.appendClassPath(new ByteArrayClassPath(binName, byteCode));
                CtClass ctClass = classPool.get(binName);
                logger.info("Trying to instrument method with annotation {} within class {}", ANNOTATION_NAME, className);
                byteCode = instrumentClass(ctClass);
            }
        } catch (Exception exception) {
            logger.error("Exception during instrumentation: {}", exception.getMessage());
        }

        return byteCode;
    }

    private byte[] instrumentClass(CtClass ctClass) throws CannotCompileException, IOException {
        CtMethod[] methods = ctClass.getDeclaredMethods();
        List<CtMethod> annotatedMethods = Arrays.stream(methods)
                .filter(method -> method.hasAnnotation(ANNOTATION_NAME))
                .collect(Collectors.toList());
        logger.info("List of annotated methods: {}", annotatedMethods);

        for (CtMethod method : annotatedMethods) {
            method.addLocalVariable("startTime", CtClass.longType);
            var methodName = method.getName();
            var beforeMessage = String.format("Starting measuring of %s method", methodName);
            method.insertBefore(String.format("logger.info(\"%s\");", beforeMessage));
            method.insertBefore("startTime = System.nanoTime();");
            method.insertAfter("logger.info(\"Execution Duration " + methodName
                    + " (nano sec): \"+ (System.nanoTime() - startTime) );");
        }
        byte[] bytes = ctClass.toBytecode();
        ctClass.detach();
        logger.info("Instrumentation of {} complete", ctClass.getName());

        return bytes;
    }
}