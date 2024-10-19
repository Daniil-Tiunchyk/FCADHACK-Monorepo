# Используем официальный образ OpenJDK 21 с поддержкой контейнеров
FROM eclipse-temurin:21-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл сборки приложения
COPY target/support-service-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 8080 для доступа
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
