# Usa una imagen base de Java 17 del proyecto Eclipse Temurin
FROM eclipse-temurin:17-jdk-jammy


# Argumento para el JAR
ARG JAR_FILE=target/*.jar

# Copia el archivo JAR de tu aplicación a la imagen
COPY ${JAR_FILE} app.jar

# Expone el puerto en el que corre tu aplicación dentro del contenedor
EXPOSE 8081

# Comando para ejecutar la aplicación cuando el contenedor inicie
ENTRYPOINT ["java","-jar","/app.jar"]