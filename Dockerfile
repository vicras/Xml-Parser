FROM gradle:8.4.0-jdk21
ENV JAVA_OPTS ""
RUN mkdir -p /srv/
COPY build/libs/migration-0.0.1-SNAPSHOT.jar /srv/app.jar
WORKDIR /srv
STOPSIGNAL SIGINT
ENTRYPOINT java $JAVA_OPTS -jar app.jar
EXPOSE 8080
