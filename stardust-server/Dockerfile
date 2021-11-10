FROM java:8
EXPOSE 8881
LABEL maintainer=hongtao

ENV JAR_ARG = ""
ENV JAVA_OPTS = ""
VOLUME ["/tmp", "/log"]
ADD stardust-server-1.0-SNAPSHOT.jar  /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar ${JAR_ARG}

