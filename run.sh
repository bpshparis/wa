JAVA_HOME=/opt/JDK/jre
JAR_HOME=WebContent/WEB-INF/lib
CLASSPATH=WebContent/WEB-INF/classes/
MAIN_CLASS=com.bpshparis.wa.$1

for jar in $(ls $JAR_HOME/*.jar); do
  CLASSPATH="$CLASSPATH:$jar"
done

echo $CLASSPATH

$JAVA_HOME/bin/java -cp "$CLASSPATH" "$MAIN_CLASS" "$@"

