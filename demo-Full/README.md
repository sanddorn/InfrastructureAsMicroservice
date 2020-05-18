# Virtual Machine

```shell script
cd Environment
vagrant up
```

# Nomad Configuration
```shell script
vagrant upload ../backend-docker/src/deployment/backend.nomad
vagrant upload ../frontend/src/main/deployment/frontend.nomad
```

# Backend Deployment
```shell script
vagrant ssh
consul kv put config/frontend/backend.username admin
consul kv put config/frontend/backend.password admin
nomad run backend.nomad
nomad status backend
``` 

should:

```text
ID            = backend
Name          = backend
Submit Date   = 2020-05-20T14:36:25Z
Type          = service
Priority      = 50
Datacenters   = dc1
Status        = running
Periodic      = false
Parameterized = false

Summary
Task Group  Queued  Starting  Running  Failed  Complete  Lost
docker      0       0         2        0       0         0

Latest Deployment
ID          = 3e9f2fed
Status      = running
Description = Deployment is running

Deployed
Task Group  Desired  Placed  Healthy  Unhealthy  Progress Deadline
docker      2        2       0        0          2020-05-20T14:46:25Z

Allocations
ID        Node ID   Task Group  Version  Desired  Status   Created  Modified
57ea17d2  cf12e87d  docker      0        run      running  7s ago   3s ago
de655802  cf12e87d  docker      0        run      running  7s ago   3s ago

```

# Frontend Deployment
```shell script
vagrant ssh
nomad run frontend.nomad
nomad status frontend
``` 

should:

```text
ID            = frontend
Name          = frontend
Submit Date   = 2020-05-24T11:21:44Z
Type          = service
Priority      = 50
Datacenters   = dc1
Status        = running
Periodic      = false
Parameterized = false

Summary
Task Group  Queued  Starting  Running  Failed  Complete  Lost
webs        0       0         1        0       0         0

Latest Deployment
ID          = 757b96e1
Status      = successful
Description = Deployment completed successfully

Deployed
Task Group  Desired  Placed  Healthy  Unhealthy  Progress Deadline
webs        1        1       1        0          2020-05-24T11:38:37Z

Allocations
ID        Node ID   Task Group  Version  Desired  Status   Created    Modified
8020a35e  a5cf99e5  webs        0        run      running  4m31s ago  2m14s ago

```

# Start Traefik
```
vagrant ssh
./traefik -c traefik.toml
```

View traefik GUI on `http://localhost:4888`

View Hero-Demo on `http://frontend-webs-frontend.consul.localhost:8080/hero`

