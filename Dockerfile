FROM eclipse-temurin:24-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etape 2 : Executer l'application (JRE 24)
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# AJOUTEZ CETTE LIGNE : Donne les droits d'exécution au wrapper Maven
RUN chmod +x ./mvnw

# Exposer le port par defaut de spring boot

EXPOSE 8089

#Demarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]