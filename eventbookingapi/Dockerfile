FROM openjdk:11
ADD target/springboot-mongo-docker.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
CMD ["sh", "-c", "tail -f /dev/null"]