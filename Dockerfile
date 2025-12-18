FROM eclipse-temurin:24-jdk AS build
WORKDIR /app
COPY . .

# AJOUTEZ CETTE LIGNE : Donne les droits d'exécution au wrapper Maven
RUN chmod +x ./mvnw

RUN ./mvnw clean package

# Etape 2 : Executer l'application (JRE 24)
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar



# Exposer le port par defaut de spring boot

EXPOSE 8089

#Demarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]