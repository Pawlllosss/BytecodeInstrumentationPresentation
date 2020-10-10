package transformer;

import javassist.ByteArrayClassPath;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MatterDTOConverterTransformer implements ClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(MatterDTOConverterTransformer.class);
    private static final String MATTER_DTO_CONVERTER_CLASS_NAME = "pl.oczadly.example.webapp.matter.boundary.dto.MatterDTOConverter";
    public static final String METHOD_NAME = "toDTO";

    private ClassLoader classLoader;

    public MatterDTOConverterTransformer(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;

        try {
            String filepathClassName = MATTER_DTO_CONVERTER_CLASS_NAME.replace('.', '/');
            if (className.equals(filepathClassName) && loader.equals(classLoader)) {
                logger.info("Class name equals and class loader equals");
                ClassPool classPool = new ClassPool(true);
                classPool.appendClassPath(new LoaderClassPath(classLoader));
                classPool.appendClassPath(new ByteArrayClassPath(MATTER_DTO_CONVERTER_CLASS_NAME, byteCode));
                CtClass ctClass = classPool.get(MATTER_DTO_CONVERTER_CLASS_NAME);
                logger.info("Trying to modify behaviour of class {}", MATTER_DTO_CONVERTER_CLASS_NAME);
                byteCode = instrumentClass(ctClass);
            }
        } catch (Exception exception) {
            logger.error("Exception during instrumentation: {}", exception.getMessage());
        }

        return byteCode;
    }

    private byte[] instrumentClass(CtClass ctClass) throws CannotCompileException, IOException, NotFoundException {
        CtMethod method = ctClass.getDeclaredMethod(METHOD_NAME);
        logger.info("Trying to instrument method: {}", METHOD_NAME);

        method.insertAt(13, "matterDTO.setName(matter.getName() + \"_INSTRUMENTED\");");

        byte[] bytes = ctClass.toBytecode();
        ctClass.detach();
        logger.info("Instrumentation of method {} complete", METHOD_NAME);

        return bytes;
    }
}