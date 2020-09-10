# This declares a job named "docs". There can be exactly one
# job declaration per job file.
job "backend" {
  # Specify this job should run in the region named "us". Regions
  # are defined by the Nomad servers' configuration.
  region = "global"

  # Spread the tasks in this job between us-west-1 and us-east-1.
  datacenters = ["dc1"]

  # Run this job as a "service" type. Each job type has different
  # properties. See the documentation below for more examples.
  type = "service"

  # Specify this job to have rolling updates, one-at-a-time, with
  # 30 second intervals.
  update {
    stagger = "30s"
    max_parallel = 1
  }

  # A group defines a series of tasks that should be co-located
  # on the same client (host). All tasks within a group will be
  # placed on the same host.
  group "docker" {
    # Specify the number of these tasks we want.
    count = 2

    # Create an individual task (unit of work). This particular
    # task utilizes a Docker container to front a web application.
    task "backend" {
      # Specify the driver to be "docker". Nomad supports
      # multiple drivers.
      driver = "docker"

      # Configuration is specific to each driver.
      config {
        image = "sanddorn/infrastructure-as-microservice-demo"
        # if you have a "normal" german connection: pre fetch the image...
        force_pull = false
        args = ["--", "--spring.cloud.consul.host=172.17.0.1"]
        port_map {
          http =  8081
        }
      }


      # The service block tells Nomad how to register this service
      # with Consul for service discovery and monitoring.
      service {
        # This tells Consul to monitor the service on the port
        # labelled "http". Since Nomad allocates high dynamic port
        # numbers, we use labels to refer to them.
        port = "http"

        check {
          type = "http"
          path = "/actuator/health"
          interval = "90s"
          timeout = "2s"
        }
      }

      # Specify the maximum resources required to run the task,
      # include CPU, memory, and bandwidth.
      resources {
        cpu = 500
        # MHz
        memory = 128
        # MB

        network {
          mbits = 100

          # This requests a dynamic port named "http". This will
          # be something like "46283", but we refer to it via the
          # label "http".
          port "http" {
          }
        }
      }
    }
  }
}
