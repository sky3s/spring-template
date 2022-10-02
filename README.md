# spring-template

## Milestones:

### Milestone 0 | Base Code:
Basic configuration completed. Application can be run. This is base module.

### Milestone 2:
Message & error handling features [message-module] added. 

### Milestone 3:
OpenApi features [openapi-module] added.

### Milestone 4:
Hibernate JPA features [database-module] added.

### Milestone 5:
RestTemplate features [rest-template-module] added.

### Milestone 6:
Security features [security-module] added;


## Notes

### Docker build & run
``` bash
  docker build -t "spring-template:0.0.1-SNAPSHOT" .
  docker run -d -p 8072:8070 5bad61435c43
```

### Run Application Without Tomcat:
``` bash
    ./mvnw package && java -jar target/spring-template.war
``` 