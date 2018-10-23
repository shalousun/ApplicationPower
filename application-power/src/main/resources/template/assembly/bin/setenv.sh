
# set jvm params

export JAVA_OPTS="$JAVA_OPTS -Xms512m"
export JAVA_OPTS="$JAVA_OPTS -Xmx512m"

# The hotspot server JVM has specific code-path optimizations
# which yield an approximate 10% gain over the client version.
export JAVA_OPTS="$JAVA_OPTS -server"

# Basically tell the JVM not to load awt libraries
export JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=true"

# set encoding
export JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=utf-8"

# OOM
export JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError"

# set garbage collector
export JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled"

# print gc log
# export JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"
# export JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCTimeStamps"
# export JAVA_OPTS="$JAVA_OPTS -Xloggc:${LOGS_DIR}/gc.log"

# only for jdk 1.7
#export JAVA_OPTS="$JAVA_OPTS -XX:MaxPermSize="256m