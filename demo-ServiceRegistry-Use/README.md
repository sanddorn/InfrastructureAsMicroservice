# Consul Service
```
docker run -d --name=dev-consul -p 8500:8500 consul:1.6.1

docker exec dev-consul consul kv put config/frontend/backend.username admin
docker exec dev-consul consul kv put config/frontend/backend.password admin
```

# Demo
Spring Boot Demo Application

```
mvn clean install
(cd backend && mvn spring-boot:run)
(cd frontend && mvn spring-boot:run)
```

Application URL: `http://localhost:8080/hero`
Backend URL (See api/hero-api.yml for details): `http://localhost:8081/api/`
