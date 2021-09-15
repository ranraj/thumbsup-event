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

GET

curl -X GET http://localhost:8080/events

POST

http://localhost:8080/events

```
    {
        "id_str": "61417d7d2b7e6878f936bf85",
        "created_at": null,
        "question": "What is you most favourite version controll system?",
        "image_url": null,
        "user": null
    }
```    