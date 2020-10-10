package transformer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

//https://javapapers.com/core-java/java-instrumentation/
//this class will be registered with instrumentation agent
public class DurationTransformer implements ClassFileTransformer {
    public byte[] transform(ClassLoader loader, String className,
                            Class classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;

        if (className.equals("part1/SamplePrinter")) {
            System.out.println(String.format("Instrumenting %s", className));
            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(
                        classfileBuffer));
                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod method : methods) {
                    method.addLocalVariable("startTime", CtClass.longType);
                    var methodName = method.getName();
                    var beforeMessage = String.format("Starting measuring of %s method", methodName);
                    method.insertBefore(String.format("System.out.println(\"%s\");", beforeMessage));
                    method.insertBefore("startTime = System.nanoTime();");
                    method.insertAfter("System.out.println(\"Execution Duration "
                            + "(nano sec): \"+ (System.nanoTime() - startTime) );");
                }
                byteCode = ctClass.toBytecode();
                ctClass.detach();
                System.out.println("Instrumentation complete.");
            } catch (Throwable ex) {
                System.err.println("Exception: " + ex);
                ex.printStackTrace();
            }
        }
        return byteCode;
    }
}