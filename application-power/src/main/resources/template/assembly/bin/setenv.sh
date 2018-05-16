
# set jvm params

export JAVA_OPTS="$JAVA_OPTS -Xms512m"
export JAVA_OPTS="$JAVA_OPTS -Xmx512m"
export JAVA_OPTS="$JAVA_OPTS -Xss256K"

# The hotspot server JVM has specific code-path optimizations
# which yield an approximate 10% gain over the client version.
export JAVA_OPTS="$JAVA_OPTS -server"

# Basically tell the JVM not to load awt libraries
export JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=true"

# set encoding
export JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=utf-8"

# set garbage collector
# export java_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"

# only for jdk 1.7
#export JAVA_OPTS="$JAVA_OPTS -XX:MaxPermSize="256m