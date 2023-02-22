FROM openjdk:19
COPY build/libs/ExamHomework-0.0.1-SNAPSHOT.jar examhomework-docker.jar
ENTRYPOINT ["java", "-jar", "examhomework-docker.jar"]