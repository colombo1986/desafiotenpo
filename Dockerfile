# Usa una imagen base compatible con Java 21
FROM eclipse-temurin:21-jre-alpine

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR generado al contenedor
COPY target/cvergara-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Definir las variables de entorno necesarias para el application.yml
ENV DB_HOST=my_postgres \
    DB_PORT=5432 \
    DB_NAME=mydb \
    DB_USER=myuser \
    DB_PASSWORD=mypassword \
    REDIS_HOST=my_redis \
    REDIS_PORT=6379 \
    SPRING_DATASOURCE_URL=jdbc:postgresql://my_postgres:5432/mydb \
    SPRING_DATASOURCE_USERNAME=myuser \
    SPRING_DATASOURCE_PASSWORD=mypassword

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

