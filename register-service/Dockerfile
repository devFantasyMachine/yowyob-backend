FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY ./target/register_service-1.0.0.jar registerservice.jar
EXPOSE $SERVER_PORT

ENTRYPOINT exec java $JAVA_OPTS -jar registerservice.jar -Dawt.useSystemAAFontSettings=on -Dswing.aatext=true

# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar userservice.jar





