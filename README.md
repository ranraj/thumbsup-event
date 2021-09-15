# thumbsup-event
thumbsup event manager

Build
```
mvn install
```

Run 

```
java -jar opinion-event-application/target/opinion-event-application-0.0.1-SNAPSHOT.jar
```

API

### Create
```
curl --location --request POST 'http://localhost:8080/events' \
--header 'Content-Type: application/json' \
--data-raw '{                
        "question": "REST client for testing?"                
}'
```

### Fetch All
```
curl --location --request GET 'http://localhost:8080/events'
```

Lombok STS integration

[Lombo](https://i.stack.imgur.com/3bxKE.png)