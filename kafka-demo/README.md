# Kafka Demo

Voraussetzung: Kafka ist über localhost:9092 erreichbar.

```
mvn clean install
mvn spring-boot:run
```

Die Anwendung erstellt das Kafka Topic *kafka-demo-stream*.
Die Anwendung ist sowohl ein Publisher als auch ein Subscriber dieses Topics.

* Publisher: Veröffentlicht Nachrichten im *kafka-demo-stream*
* Subscriber: Erhält Nachrichten aus dem *kafka-demo-stream* Topic und loggt diese.

Um eine Nachricht zu veröffentlichen, muss ein HTTP Request (Beispiel [message.http](message.http)) an die Anwendung gesandt werden.
