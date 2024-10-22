FROM amazoncorretto:17.0.4

WORKDIR /app
COPY ./target/sgadnuxt-0.0.1-SNAPSHOT.jar .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target
#RUN ./mvnw dependency:go-offline
COPY ./src ./src

#RUN ./mvnw clean package -DskipTests


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sgadnuxt-0.0.1-SNAPSHOT.jar"]