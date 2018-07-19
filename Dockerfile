FROM openjdk:8-jdk-alpine
ADD target/PreferenceRestService.jar ws_PreferenceRestService_sf.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","ws_PreferenceRestService_sf.jar"]