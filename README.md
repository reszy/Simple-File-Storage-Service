# Simple-File-Storage-Service
REST microservice for file storage

## How to start
1. Build image
```
mvn install -Pdocker
docker build .
```
2.  Use in docker-compose
```yml
services:
  file-storage: file-storage:0.0.1-SNAPSHOT
```

## Disclaimer
Service is not prod ready

# API
TODO