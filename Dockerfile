# Etapa 1: compilar usando Maven em container
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -e -DskipTests -Dfile.encoding=UTF-8


# Verifique o conteúdo do arquivo application.yml
RUN cat /app/target/classes/application.yml

# Etapa 2: rodar o .jar gerado
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
