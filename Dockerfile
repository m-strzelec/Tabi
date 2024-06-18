FROM maven:latest

WORKDIR tabi/
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
