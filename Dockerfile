# Используем официальный образ OpenJDK 21 с поддержкой контейнеров
FROM eclipse-temurin:21-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл сборки приложения
COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
