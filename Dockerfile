FROM adoptopenjdk/openjdk13-openj9:latest
COPY deploy/letter-classification-0.0.1-SNAPSHOT.jar /opt/letter-classification/
WORKDIR /opt/letter-classification/
CMD ["java", "-jar", "/opt/letter-classification/letter-classification-0.0.1-SNAPSHOT.jar"]
