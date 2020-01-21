JAVA_HOME=/opt/JDK/jre
JAR_HOME=WebContent/WEB-INF/lib
CLASSPATH=WebContent/WEB-INF/classes/
PACKAGE=com.bpshparis.wa


for jar in $(ls $JAR_HOME/*.jar); do
  CLASSPATH="$CLASSPATH:$jar"
done

echo Run $@ ...

$JAVA_HOME/bin/java -cp "$CLASSPATH" "$PACKAGE"."$@"

