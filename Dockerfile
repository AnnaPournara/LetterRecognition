FROM adoptopenjdk/openjdk13-openj9:latest
COPY deploy/letter-classification-0.0.1-SNAPSHOT.jar /opt/LetterRecognition/
WORKDIR /opt/LetterRecognition/
CMD ["java", "-jar", "/opt/LetterRecognition/letter-classification-0.0.1-SNAPSHOT.jar"]
