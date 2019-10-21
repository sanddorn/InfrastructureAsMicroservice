# Virtual Machine

```
cd Environment
vagrant up
```

# Nomad Configuration
```
vagrant upload backend-docker/src/deployment/backend.nomad
vagrant upload frontend/src/main/deployment/frontend.nomad
```

# Backend Deployment
```
vagrant ssh
nomad apply backend.nomad
nomad status backend
``` 

should:

```

```

# Frontend Deployment
```
vagrant ssh
nomad apply frontend.nomad
nomad status frontend
``` 

should:

# Start Traefik
```
vagrant ssh
./traefik -c traefik.toml
```

View traefik GUI on `http://localhost:4888`

View Hero-Demo on `http://localhost:8080`

