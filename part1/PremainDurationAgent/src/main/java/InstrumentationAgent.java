import transformer.DurationTransformerWithArguments;

import java.lang.instrument.Instrumentation;
import java.util.regex.Pattern;

public class InstrumentationAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("Executing premain!");
//        instrumentation.addTransformer(new DurationTransformer());
        String[] classNames = agentArgs.split(Pattern.quote("+"));
        instrumentation.addTransformer(new DurationTransformerWithArguments(classNames));
    }
}
