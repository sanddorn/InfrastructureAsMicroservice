# Infrastructure As Microservice Talk
You can find here the slides and the sample code of our talk "Infrastructure as Microservice - Alternativen zum Monolithen Kubernetes" that we presented on Softwerkskammer at 23rd October 2019.

The code samples are tested with
- Maven 3.6.2
- Java 11
- Docker 19.03.3-rc1
- Consul 1.6.1
- Vagrant 2.2.5
- Nomad 0.9.6
- Traefic 1.7.30

## Step 0 - Classical Client Server Architecture

In `demo-Start`, you can find the starting point for our journey to get an automated operation without Kubernetes.

For running the demo:

```
cd demo-Start
mvn clean install
(cd backend && mvn spring-boot:run)
(cd frontend && mvn spring-boot:run)
```

Application URL: `http://localhost:8080/hero`

Backend URL (See api/hero-api.yml for details): `http://localhost:8081/api/`
Username: `admin`
Password: `admin`
For example REST-API: `http://localhost:8081/api/hero/heros`

## Step 1 - Retrieve Environment from Key-Value Store
In `demo-KeyValueStore`, you can find the transformation from loading configuration from properties to loading configuration from key value store.

For running the demo:

Consul preparation:

```
docker run -d --name=dev-consul -p 8500:8500 consul:1.6.1

docker exec dev-consul consul kv put config/frontend/backend.url http://localhost:8081/api
docker exec dev-consul consul kv put config/frontend/backend.username admin
docker exec dev-consul consul kv put config/frontend/backend.password admin
```
Starting our demo application:

```
cd demo-KeyValueStore
mvn clean install
(cd backend && mvn spring-boot:run)
(cd frontend && mvn spring-boot:run)
```

Application URL: `http://localhost:8080/hero`

Backend URL (See api/hero-api.yml for details): `http://localhost:8081/api/`
Username: `admin`
Password: `admin`
For example REST-API: `http://localhost:8081/api/hero/heros`

You can see that the application is loading the configuration from Consul in the log of frontend:
```
2019-10-22 18:23:38.929  INFO 7606 --- [  restartedMain] b.c.PropertySourceBootstrapConfiguration : Located property source: CompositePropertySource {name='consul', propertySources=[ConsulPropertySource {name='config/frontend/'}, ConsulPropertySource {name='config/application/'}]}
```

## Step 2 - Push Service Infomation into Service Registry
In `demo-ServiceRegistry-Push`, you can find how to push our service information into a service registry.

Consul preparation:

```
docker run -d --name=dev-consul -p 8500:8500 consul:1.6.1

docker exec dev-consul consul kv put config/frontend/backend.username admin
docker exec dev-consul consul kv put config/frontend/backend.password admin
```
Starting our demo application:

```
cd demo-ServiceRegistry-Push
mvn clean install
(cd backend && mvn spring-boot:run)
(cd frontend && mvn spring-boot:run)
```

Application URL: `http://localhost:8080/hero`

Backend URL (See api/hero-api.yml for details): `http://localhost:8081/api/`
Username: `admin`
Password: `admin`
For example REST-API: `http://localhost:8081/api/hero/heros`

You can see the registration information of our service in `http://localhost:8500/ui/dc1/services/backend`


## Step 3 - Use Service Infomation from Service Registry (Service Discovery)
In `demo-ServiceRegistry-Use`, you can find how to use our service information from a service registry.

Consul preparation:

```
docker run -d --name=dev-consul -p 8500:8500 consul:1.6.1

docker exec dev-consul consul kv put config/frontend/backend.username admin
docker exec dev-consul consul kv put config/frontend/backend.password admin
```
Starting our demo application:

```
cd demo-ServiceRegistry-Use
mvn clean install
(cd backend && mvn spring-boot:run)
(cd frontend && mvn spring-boot:run)
```

Application URL: `http://localhost:8080/hero`

Backend URL (See api/hero-api.yml for details): `http://localhost:8081/api/`
Username: `admin`
Password: `admin`
For example REST-API: `http://localhost:8081/api/hero/heros`

You can see the registration information of our service in `http://localhost:8500/ui/dc1/services/backend`.

You can see that the application is using the serivce information fro the service registry feature of Consul in the log of frontend:
```
2019-10-22 18:38:35.839  INFO 9456 --- [  restartedMain] d.b.h.f.universum.HeroServiceRestClient  : Found Service URL: 'http://localhost:8081''
2019-10-22 18:38:35.842  INFO 9456 --- [  restartedMain] d.b.h.f.universum.HeroServiceRestClient  : ServicePath: 'http://localhost:8081/api'

```

## Step 4 - Scaling
In `demo-Full`, you can find how to scale our application automatically in a cluster with Nomad.

Nomad preparation:
```
cd Environment
vagrant up
vagrant upload backend-docker/src/deployment/backend.nomad
vagrant upload frontend/src/main/deployment/frontend.nomad

```
