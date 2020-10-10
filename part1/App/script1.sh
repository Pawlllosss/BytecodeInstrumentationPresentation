COMMAND=$1
ARGUMENT=$2
APP_PATH=build/libs/App1-1.0-SNAPSHOT.jar
AGENT_PATH=../PremainDurationAgent/build/libs/PremainDurationAgent-1.0-SNAPSHOT.jar

case $COMMAND in

  decompile)
      echo "Decompilling path"
      javap -c build/classes/java/main/part1/SamplePrinter.class
    ;;

  run)
      echo "Running app"
    	java -jar $APP_PATH
    ;;

  run_with_example_args)
      EXAMPLE_ARGS=part1/SamplePrinter+part1/Main
      echo "Runnining with agent with example args: $EXAMPLE_ARGS"
    	java -javaagent:$AGENT_PATH=$EXAMPLE_ARGS -jar $APP_PATH
    ;;

  run_with_args)
      echo "Runnining with agent with args: $ARGUMENT"
    	java -javaagent:$AGENT_PATH=$ARGUMENT -jar $APP_PATH
    ;;
esac