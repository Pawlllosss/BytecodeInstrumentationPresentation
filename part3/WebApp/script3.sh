COMMAND=$1
APP_NAME=webapp-0.0.1-SNAPSHOT.jar
APP_PATH=build/libs/webapp-0.0.1-SNAPSHOT.jar
AGENT_PATH=../PremainDurationAgent/build/libs/DynamicDurationAgent-1.0-SNAPSHOT.jar
DYNAMIC_AGENT_LOADER_PATH=../DynamicAgentLoader/build/libs/dynamic_agent_loader-1.0-SNAPSHOT.jar

case $COMMAND in

  list_jvms)
      jps
    ;;

  run)
      echo "Running the app"
    	java -jar $APP_PATH
    ;;

  attach_agent)
      ARGS=$2
      echo "Attaching agent to the app with args $ARGS"
      PID=$(jps | grep $APP_NAME | awk '{print $1}')
      java -jar $DYNAMIC_AGENT_LOADER_PATH $PID $AGENT_PATH $ARGS
    ;;

  attach_agent_with_example_args)
      echo "Attaching agent to the app with example args pl.oczadly.example.webapp.matter.control.MatterService"
      PID=$(jps | grep $APP_NAME | awk '{print $1}')
      EXAMPLE_ARGS=pl.oczadly.example.webapp.matter.control.MatterService
      java -jar $DYNAMIC_AGENT_LOADER_PATH $PID $AGENT_PATH $EXAMPLE_ARGS
    ;;
esac