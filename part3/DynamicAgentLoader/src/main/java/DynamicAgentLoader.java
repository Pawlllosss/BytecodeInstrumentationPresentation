import com.sun.tools.attach.VirtualMachine;

// based on https://github.com/jakubhalun/tt2016_attach_api_agent_loader
public class DynamicAgentLoader {

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Please provide process id, path to agent jar and agent arguments containing transformed classes");
            System.exit(-1);
        }

        String processId = args[0];
        String agentJarPath = args[1];
        String agentArguments = args[2];
        VirtualMachine vm = VirtualMachine.attach(processId);
        vm.loadAgent(agentJarPath, agentArguments);
        vm.detach();
    }
}
