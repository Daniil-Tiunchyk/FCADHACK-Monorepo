# Используем официальный образ Maven для сборки
FROM maven:3.9.0-eclipse-temurin-21 AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и зависимости
COPY pom.xml .
COPY src ./src

# Собираем приложение
RUN mvn clean package -DskipTests

# Используем официальный образ OpenJDK 21 с поддержкой контейнеров для выполнения
FROM eclipse-temurin:21-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл сборки приложения из предыдущего этапа
COPY --from=builder /app/target/support-service-0.0.1-SNAPSHOT.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
