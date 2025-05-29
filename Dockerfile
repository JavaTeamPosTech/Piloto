# Etapa 1: compilar usando Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dfile.encoding=UTF-8

# Etapa 2: rodar a aplicação
FROM eclipse-temurin:21
WORKDIR /app

# Instala netcat para o wait-for-it funcionar
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

# Copia o JAR
COPY --from=build /app/target/*.jar app.jar

# Copia o wait-for-it.sh
COPY wait-for-it.sh /app/wait-for-it.sh

# Torna o script executável
RUN chmod +x /app/wait-for-it.sh

# Comando para aguardar o Postgres e iniciar a app
CMD ["/app/wait-for-it.sh", "db", "5432", "--", "java", "-jar", "app.jar"]
