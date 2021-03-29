FROM openjdk:11-jdk-slim-sid

ENV APP_NAME laboratory-item-store
WORKDIR /var/opt

COPY target/laboratory-item-store-1.0.0-SNAPSHOT.jar /var/opt/laboratory-item-store.jar

USER nobody
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=demo", "laboratory-item-store.jar"]
