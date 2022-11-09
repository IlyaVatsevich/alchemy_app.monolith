FROM openjdk:11
MAINTAINER IV
COPY target/alchemy_app-1.0.0.jar alchemy_app-1.0.0.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/alchemy_app-1.0.0.jar"]