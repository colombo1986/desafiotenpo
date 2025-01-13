# Levantar la Aplicación con Docker Compose

Este proyecto utiliza **Docker Compose** para gestionar la aplicación y sus dependencias, incluyendo una base de datos PostgreSQL y un servidor Redis. Sigue los pasos a continuación para iniciar el entorno completo.

La aplicación fue desarrollada con **Spring WebFlux** para garantizar mayor escalabilidad y mejor manejo de cargas concurrentes, aprovechando su modelo reactivo y no bloqueante.

---

## **Requisitos Previos**

Asegúrate de tener instalados los siguientes componentes:

- [Docker](https://www.docker.com/) (versión 20.10 o superior).
- [Docker Compose](https://docs.docker.com/compose/) (versión 1.29 o superior).

---

## **Servicios en el `docker-compose.yml`**

Este archivo define tres servicios:

1. **`app`**: La aplicación principal, basada en la imagen `cristophervergara/cvergara:latest`.
    - Escucha en el puerto `8080`.
    - Se conecta a los servicios PostgreSQL y Redis.

2. **`postgres`**: Base de datos PostgreSQL.
    - Escucha en el puerto `5432`.
    - Configurado con el usuario, contraseña y base de datos definidos.

3. **`redis`**: Servidor Redis.
    - Escucha en el puerto `6379`.

---

## **Instrucciones para Levantar los Servicios**

1. Clona el repositorio o asegúrate de tener el archivo `docker-compose.yml` en tu máquina.

2. Abre una terminal y navega al directorio donde se encuentra el archivo `docker-compose.yml`.

3. Ejecuta el siguiente comando para construir y levantar los contenedores:

   ```bash
   docker-compose up --build
   ```

   Esto realizará lo siguiente:
    - Construirá y levantará los contenedores para `app`, `postgres`, y `redis`.
    - Conectará automáticamente los servicios entre sí mediante la red interna de Docker Compose.

4. Para correr los servicios en segundo plano (modo `detached`), usa:

   ```bash
   docker-compose up -d
   ```

---

## **Acceder a la Aplicación**

0. El repositorio de GitHub esta disponible en:

   ```
   https://github.com/colombo1986/desafiotenpo
   ```


1. La aplicación cuenta con documentación interactiva en Swagger disponible en:

   ```
   http://localhost:8080/swagger-ui/index.html#/
   ```

2. **Endpoints disponibles:**

    - **`/add`**:
        - URL: `http://localhost:8080/add`
        - Método: `GET`
        - Parámetros: `num1` y `num2` (números enteros).
        - Ejemplo: `http://localhost:8080/add?num1=5&num2=5`
        - Descripción: Suma ambos números y aplica un porcentaje adicional obtenido de un servicio externo.
            - Ejemplo: Si `num1=5` y `num2=5` y el servicio externo retorna un `10%`, el resultado será: `(5 + 5) + 10% = 11`.

    - **`/history`**:
        - URL: `http://localhost:8080/history`
        - Método: `GET`
        - Parámetros: `page` (número de página) y `size` (tamaño de la página).
        - Ejemplo: `http://localhost:8080/history?page=0&size=10`
        - Descripción: Devuelve el historial de llamadas realizadas a la API en formato JSON.
            - Ejemplo de respuesta:
              ```json
              {
                "content": [
                  {
                    "id": 10,
                    "insertdate": "2025-01-12T09:31:45.478467",
                    "endpoint": "http://localhost:8080/history?page=0&size=10",
                    "parameters": "{page=0, size=10}",
                    "responseStatus": null,
                    "responseBody": null
                  }
                ]
              }
              ```

---

## **Detener los Servicios**

Para detener y eliminar todos los contenedores creados por Docker Compose, usa:

```bash
docker-compose down
```

---

## **Verificar los Logs**

Para revisar los logs de los contenedores en tiempo real:

```bash
docker-compose logs -f
```

---

## **Notas Adicionales**

1. **Persistencia de Datos**:
    - Los datos de PostgreSQL se almacenan en el volumen `db-data`.
    - Los datos de Redis se almacenan en el volumen `redis-data`.

2. **Modificar Configuraciones**:
    - Puedes cambiar las configuraciones de los servicios (como usuarios, contraseñas o puertos) directamente en el archivo `docker-compose.yml`.

3. **Reconstruir la Imagen de la Aplicación**:
    - Si realizas cambios en el código de la aplicación, asegúrate de reconstruir la imagen:

      ```bash
      docker-compose up --build
      ```



