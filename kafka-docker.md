# Kafka Lokal ausführen

```
docker-compose up -d
```

## Akhq

Akhq ist ein UI für Kafka.

Installation: https://akhq.io/docs/installation.html

### Docker Installation
```
docker run -d \
   -p 9093:8080 \
   -v /tmp/application.yml:/app/application.yml \
   tchiotludo/akhq
```

### Standalone Installation

Basiert auf https://akhq.io/docs/installation.html

Nachdem es sich bei akhq um eine Micronaut (Java Webanwendung) handelt kann diese lokal ausgeführt werden, wenn Java 11 installiert ist.
Es empfiehlt sich, dass `.jar` zusammen mit der Konfiguration in einem Ordner im Home Verzeichnis des Users abzulegen.

* Download des aktuellen `.jar`s von https://github.com/tchiotludo/akhq/releases
* Konfigurationsdatei anlegen
* Anwendung ausführen mit `java -Dmicronaut.config.files=application.yml -jar akhq.jar`
* Anschließend ist Akhq unter [localhost:9093](http://localhost:9093/) verfügbar

### Konfiguration

Konfiguration: https://akhq.io/docs/configuration/ bzw. ein Beispiel https://github.com/tchiotludo/akhq/blob/dev/application.example.yml

Beispiel Konfiguration für ein lokal ausgeführtes Kafka Cluster:

```
micronaut:
  server:
    port: 9093

akhq:
  server:
    access-log: # Access log configuration (optional)
      enabled: true # true by default
      name: org.akhq.log.access # Logger name
      format: "[Date: {}] [Duration: {} ms] [Url: {} {}] [Status: {}] [Ip: {}] [User: {}]" # Logger format

  # default kafka properties for each clients, available for admin / producer / consumer (optional)
  clients-defaults:
    consumer:
      properties:
        isolation.level: read_committed

  # list of kafka cluster available for akhq
  connections:
    my-cluster-plain-text: # url friendly name for the cluster (letter, number, _, -, ... dot are not allowed here)
      properties: # standard kafka properties (optional)
        bootstrap.servers: "localhost:9092"
      connect:
        - name: connect-1
          url: "http://localhost:9092"
      # Ui Cluster Options (optional)
      ui-options:
        topic:
          default-view: ALL  # default list view (ALL, HIDE_INTERNAL, HIDE_INTERNAL_STREAM, HIDE_STREAM). Overrides default
          skip-consumer-groups: false # Skip loading consumer group information when showing topics. Overrides default
          skip-last-record: true  # Skip loading last record date information when showing topics.  Overrides default
          show-all-consumer-groups: true # Expand list of consumer groups instead of showing one. Overrides default.
        topic-data:
          sort: NEWEST # default sort order (OLDEST, NEWEST) (default: OLDEST).  Overrides default

  pagination:
    page-size: 25 # number of elements per page (default : 25)
    threads: 16 # Number of parallel threads to resolve page

  # Configure avro-to-json serializer
  avro-serializer:
    json.serialization.inclusions: # ObjectMapper serialization inclusions used for avro-to-json conversion for display in the UI.
    # Supports Enums in JsonInclude.Include from Jackson library
      - NON_NULL

  # Topic list display options (optional)
  topic:
    retention: 172800000 # default retention period when creating topic
    partition: 3 #  default number of partition when creating topic
    replication: 3 # default number of replicas when creating topic
    internal-regexps: # list of regexp to be considered as internal (internal topic can't be deleted or updated)
      - "^_.*$"
      - "^.*_schemas$"
      - "^.*connect-config$"
      - "^.*connect-offsets$1"
      - "^.*connect-status$"
    stream-regexps: # list of regexp to be considered as internal stream topic
      - "^.*-changelog$"
      - "^.*-repartition$"
      - "^.*-rekey$"
    skip-consumer-groups: false # Skip loading consumer group information when showing topics
    skip-last-record: false # Skip loading last record date information when showing topics
    show-all-consumer-groups: false # Expand list of consumer groups instead of showing one.
    # Retry options for topic operations
    retry:
      topic-exists: # Delay between retries when checking for existence of newly created topics. This is needed as it might take the kafka broker a few seconds to create new topics.
        delay: "3s"

  # Topic display data options (optional)
  topic-data:
    size: 50 # max record per page (default: 50)
    poll-timeout: 1000 # The time, in milliseconds, spent waiting in poll if data is not available in the buffer.
    kafka-max-message-length: 1000000 # Max message length allowed to send to UI when retrieving a list of records in bytes.

  # Ui Global Options (optional)
  ui-options:
    topic:
      default-view: ALL  # default list view (ALL, HIDE_INTERNAL, HIDE_INTERNAL_STREAM, HIDE_STREAM). Overrides default
      skip-consumer-groups: false # Skip loading consumer group information when showing topics. Overrides default
      skip-last-record: true  # Skip loading last record date information when showing topics.  Overrides default
      show-all-consumer-groups: true # Expand list of consumer groups instead of showing one. Overrides default.
    topic-data:
      sort: NEWEST # default sort order (OLDEST, NEWEST) (default: OLDEST).  Overrides default
```