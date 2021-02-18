## SpringBoot app with monitoring

## Setup
- Docker
- JDK8
- maven

## Stack
- SpringBoot
- prometheus
- promtail
- loki
- grafana

## How to run ?

1. Fire up Docker

2. Run project as is. REST endpoint should be up.
    - POST: localhost:8080/api/cal  
    `{"op": "P", "left": "3", "right": "4"}`
    -GET:  localhost:8080/api/mov

3. under project root
    $ cd docker
    $ docker-compose up

4. Done.

## What can i do?

- access Prometheus: http://localhost:9090 , in `Expression` search for log4j2_events_total and execute
- access Promtail: http://localhost:9080/ready , you should see `Ready`.
- access Loki: http://localhost:3100/metrics , http://localhost:3100/loki/api/v1/query?query=%7Bjob=%22spring-app%22%7D

## GUI
This is where Grafana is used
- acess Grafana GUI: http://localhost:3000/
    login: admin, admin

- you should see a Sample Dashboard.