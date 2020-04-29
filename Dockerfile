FROM openjdk:11-jre

ARG WD=/usr/share/fileStorage
COPY target/fileStorage*.jar ${WD}/fileStorage.jar

WORKDIR ${WD}

ENTRYPOINT ["java","-jar","fileStorage.jar"]