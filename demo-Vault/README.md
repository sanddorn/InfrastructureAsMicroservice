# Consul Service
```
docker run -d --name=dev-consul -p 8500:8500 consul:1.7.3

docker exec dev-consul consul kv put config/frontend/backend.username admin
```
# Vault Service
```
docker run --cap-add=IPC_LOCK -d  -p 8200:8200 --name=dev-vault vault:1.5.3

export VAULT_TOKEN=$(docker logs dev-vault 2>&1 | grep "Root Token" | awk '{print $3}')
docker exec -e VAULT_ADDR=http://127.0.0.1:8200 -e VAULT_TOKEN=$VAULT_TOKEN dev-vault vault kv put secret/frontend backend.password=admin
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
