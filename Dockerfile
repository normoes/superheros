FROM openjdk:8-alpine3.7
# openjdk version "1.8.0_151"
# OpenJDK Runtime Environment (IcedTea 3.6.0) (Alpine 8.151.12-r0)
# OpenJDK 64-Bit Server VM (build 25.151-b12, mixed mode)

ENV PORT 8080

ENV PROTOCOL_MONGO "mongodb"
ENV MONGO_HOST "localhost"
ENV MONGO_PORT 27017

ENV JAVA_OPTS ""

WORKDIR /data

COPY superhero.jar /data/superhero.jar
# add file modification time
RUN sh -c 'touch /data/superhero.jar'

EXPOSE $PORT

LABEL com.aucobo.name="superhero service" \
          com.aucobo.description="superhero backend" \
          com.aucobo.maintainer="norman.moeschter@gmail.com"

ENTRYPOINT ["sh", "-c", "java -Dspring.data.mongodb.host=$MONGO_HOST -Dspring.data.mongodb.port=$MONGO_PORT -Dserver.port=$PORT -Djava.security.egd=file:/dev/./urandom -Xms256m -Xmx1024m -server $JAVA_OPTS -jar ./superhero.jar"]
