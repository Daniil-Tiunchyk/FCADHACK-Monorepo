# Support Service

Support Service - это микросервис на Java 21, который получает сообщения из Kafka, сохраняет их в базе данных PostgreSQL и предоставляет REST API для доступа к этим сообщениям.

## Содержание

- [Support Service](#support-service)
  - [Содержание](#содержание)
  - [Технологии](#технологии)
  - [Структура проекта](#структура-проекта)
  - [Установка и запуск](#установка-и-запуск)
    - [Клонирование репозитория](#клонирование-репозитория)
    - [Настройка базы данных](#настройка-базы-данных)
    - [Настройка Kafka](#настройка-kafka)
    - [Конфигурация приложения](#конфигурация-приложения)
    - [Сборка и запуск приложения](#сборка-и-запуск-приложения)
      - [Через Maven](#через-maven)
      - [Через Docker](#через-docker)
  - [Использование](#использование)
    - [Отправка сообщений в Kafka](#отправка-сообщений-в-kafka)
    - [REST API](#rest-api)
      - [Получить сообщение по ID](#получить-сообщение-по-id)
      - [Получить список сообщений с фильтрацией и пагинацией](#получить-список-сообщений-с-фильтрацией-и-пагинацией)
  - [Docker](#docker)
    - [Сборка Docker-образа](#сборка-docker-образа)
    - [Запуск контейнера](#запуск-контейнера)
  - [Примечания](#примечания)

## Технологии

- Java 21
- Spring Boot 3
- PostgreSQL
- Apache Kafka
- Maven
- Docker

## Структура проекта

```plaintext
supportservice/
├── src/
│   └── main/
│       ├── java/
│       │   └── com.example.supportservice/
│       │       ├── config/
│       │       │   └── KafkaConsumerConfig.java
│       │       ├── domain/
│       │       │   ├── entity/
│       │       │   │   └── SupportMessage.java
│       │       │   └── repository/
│       │       │       └── SupportMessageRepository.java
│       │       ├── service/
│       │       │   ├── SupportMessageService.java
│       │       │   └── SupportMessageServiceImpl.java
│       │       ├── util/
│       │       │   └── SupportMessageSpecification.java
│       │       └── web/
│       │           ├── controller/
│       │           │   └── SupportMessageController.java
│       │           ├── dto/
│       │           │   └── SupportMessageFilter.java
│       │           └── listener/
│       │               └── KafkaMessageListener.java
│       │       └── SupportServiceApplication.java
│       └── resources/
│           ├── application.properties
│           └── schema.sql
├── .gitattributes
├── .gitignore
├── Dockerfile
└── pom.xml
```

## Установка и запуск

### Клонирование репозитория

```bash
git clone https://github.com/FCADHACK/Admin-Panel-Backend-Java.git
cd Admin-Panel-Backend-Java
```

### Настройка базы данных

1. Установите PostgreSQL, если он еще не установлен.
2. Создайте базу данных `support_db`:

```sql
CREATE DATABASE support_db;
```

3. Создайте пользователя и назначьте ему права (если необходимо):

```sql
CREATE USER your_username WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE support_db TO your_username;
```

4. В файле `src/main/resources/application.properties` укажите параметры подключения к базе данных:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/support_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Настройка Kafka

1. Установите и запустите Apache Kafka.
2. Создайте топик `support-messages` (если он еще не существует):

```bash
kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic support-messages
```

### Конфигурация приложения

Все настройки находятся в файле `src/main/resources/application.properties`. Убедитесь, что параметры Kafka совпадают с вашей конфигурацией.

### Сборка и запуск приложения

#### Через Maven

Собрать и запустить приложение можно с помощью Maven:

```bash
# Сборка
mvn clean install

# Запуск
mvn spring-boot:run
```

#### Через Docker

Инструкции по использованию Docker находятся в разделе [Docker](#docker).

## Использование

### Отправка сообщений в Kafka

Пример сообщения в формате JSON:

```json
{
  "Email": "alla4329@yandex.ru",
  "Endpoint": "https://api.bestbank.ru/support",
  "Login": "alla4329",
  "Message": "Доброго вечера",
  "SupportLevel": "start",
  "Timestamp": 1691528400,
  "UserID": "892763239"
}
```

Для отправки сообщения в Kafka вы можете использовать консольный Kafka Producer или любой другой инструмент:

```bash
kafka-console-producer.sh --broker-list localhost:9092 --topic support-messages
```

Вставьте JSON-сообщение в консоль и нажмите Enter.

### REST API

Приложение предоставляет REST API с двумя эндпоинтами:

#### Получить сообщение по ID

- **URL**: `/api/support-messages/{id}`
- **Метод**: `GET`

**Пример запроса:**

```bash
curl -X GET http://localhost:8080/api/support-messages/1
```

#### Получить список сообщений с фильтрацией и пагинацией

- **URL**: `/api/support-messages`
- **Метод**: `GET`
- **Параметры запроса**:
  - `timestampFrom` (необязательный) - начало диапазона даты и времени (ISO 8601)
  - `timestampTo` (необязательный) - конец диапазона даты и времени (ISO 8601)
  - `userId` (необязательный) - ID пользователя (частичное или полное совпадение)
  - `supportLevel` (необязательный) - уровень поддержки (строка)
  - `messageContent` (необязательный) - содержимое сообщения (частичное или полное совпадение)
  - `email` (необязательный) - электронная почта (частичное или полное совпадение)
  - `endpoint` (необязательный) - точка доступа (частичное или полное совпадение)
  - `login` (необязательный) - логин (частичное или полное совпадение)
  - `page` (необязательный) - номер страницы (0 по умолчанию)
  - `size` (необязательный) - размер страницы (10 по умолчанию)
  - `sort` (необязательный) - сортировка (например, `timestamp,desc`)

**Пример запроса:**

```bash
curl -X GET "http://localhost:8080/api/support-messages?userId=8927&email=alla&page=0&size=5"
```

**Пример ответа:**

```json
{
  "content": [
    {
      "id": 1,
      "email": "alla4329@yandex.ru",
      "endpoint": "https://api.bestbank.ru/support",
      "login": "alla4329",
      "message": "Доброго вечера",
      "supportLevel": "start",
      "timestamp": "2023-08-08T12:00:00",
      "userId": "892763239"
    }
  ],
  "pageable": { ... },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 5,
  "number": 0,
  "sort": { ... },
  "first": true,
  "numberOfElements": 1,
  "empty": false
}
```

## Docker

### Сборка Docker-образа

Убедитесь, что Docker установлен и запущен.

1. Соберите проект с помощью Maven:

```bash
mvn clean package
```

2. Постройте Docker-образ:

```bash
docker build -t support-service:latest .
```

### Запуск контейнера

1. Запустите контейнер:

```bash
docker run -d \
  --name support-service \
  -p 8080:8080 \
  --env SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/support_db \
  --env SPRING_DATASOURCE_USERNAME=your_username \
  --env SPRING_DATASOURCE_PASSWORD=your_password \
  --env SPRING_KAFKA_BOOTSTRAP-SERVERS=host.docker.internal:9092 \
  support-service:latest
```

**Примечание**: Использование `host.docker.internal` позволяет контейнеру подключаться к сервисам на хост-машине. Пожалуйста, убедитесь, что PostgreSQL и Kafka доступны на соответствующих портах.

## Примечания

- Для упрощения логирования и отладки установите уровень логирования в `application.properties`:

```properties
logging.level.root=INFO
logging.level.org.springframework=INFO
logging.level.com.example.supportservice=DEBUG
```
