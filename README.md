# SimpleWebsocketService

## Getting Started

### Prerequisites
* java 11
* maven
* postgres
* kafka
* websocket
* docker
* docker-compose

## How to run it

1. Using Maven
    ```
   mvn clean package (from console or from maven toolbar)
   java -jar target/simple-websocket-service-0.0.1-SNAPSHOT.jar
   ```
2. Using Docker (you need postgres and kafka which run locally)
   ```
   docker build -t simple-websocket-service:latest .  
   docker run -p 8080:8080 simple-websocket-service:latest
   ```  
3. Using Docker-compose
    ```
    cd docker
    docker-compose uo
    ```