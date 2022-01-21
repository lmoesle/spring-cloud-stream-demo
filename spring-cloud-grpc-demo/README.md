# Tech Demo for Spring Cloud Streaming with gRPC and Kafka

Helpful Links:

* https://developer.okta.com/blog/2020/04/15/spring-cloud-stream
* https://dzone.com/articles/kafka-with-spring-cloud-stream - Non reactive code
* gRPC
  * https://grpc.io/docs/languages/java/quickstart/
  * https://grpc.io/docs/languages/java/basics/#client

## Getting started

```
mvn clean install
# execute SpringCloudDemoApplication
# execute GrpcClient
```

You have to set the following VM Options for the GrpcClient.

```
--add-opens java.base/jdk.internal.misc=ALL-UNNAMED
-Dio.netty.tryReflectionSetAccessible=true
--illegal-access=warn -Dio.netty.tryReflectionSetAccessible=false
```