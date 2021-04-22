#
# Build stage
#
FROM henrikbaerbak/jdk11-gradle AS build
COPY src /home/app/src
COPY build.gradle /home/app
RUN cd /home/app/ && gradle clean build

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --chown=0:0 --from=build /home/app/build/libs/*.jar ${WORKDIR}/filemanager.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/filemanager.jar"]
