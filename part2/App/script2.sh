COMMAND=$1
APP_PATH=build/libs/App2-1.0-SNAPSHOT.jar
AGENT_PATH=../AnnotationAgent/build/libs/AnnotationPremainAgent-1.0-SNAPSHOT.jar

case $COMMAND in

  run)
      echo "Running app"
    	java -jar $APP_PATH
    ;;

  run_instrumented)
      echo "Running instrumentated"
    	java -javaagent:$AGENT_PATH -jar $APP_PATH
    ;;

esac
