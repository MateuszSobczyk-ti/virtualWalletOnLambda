FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl
COPY target/walletMicroservices-0.0.1-SNAPSHOT.jar /opt/app/
EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/opt/app/walletMicroservices-0.0.1-SNAPSHOT.jar"]