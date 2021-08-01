FROM openjdk
RUN mkdir -p /app
COPY ./target/dwmongoapp-1.0-SNAPSHOT.jar /app
COPY ./config.yml /app
# WORKDIR /app
CMD ["java" "-jar" "/app/dwmongoapp-1.0-SNAPSHOT.jar" "server" "/app/config.yml"]
