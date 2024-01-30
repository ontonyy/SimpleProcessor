### App dockerization
For dockerization app need to go to directory `/configs` and there run bash commands:
* `docker compose up`
* `docker compose down`

Then if need more info just check logs by special commands like `docker logs simple-processor` for example.

* Flow: Every time when running `./dockerization.sh` going rebuild of simple-processor (spring application) service 
  image, which will be used in docker compose, then any new changes will appear if run docker-compose.

### Manual app running
* If need to run separately all services and spring application, then need to be in docker-compose.yml inside kafka
  environment `KAFKA_ADVERTISED_LISTENERS: INTERNAL://localhost:9092,EXTERNAL_SAME_HOST://kafka:29092`
* But if need to running all in one then `KAFKA_ADVERTISED_LISTENERS: INTERNAL://kafka:9092,
  EXTERNAL_SAME_HOST://localhost:29092`

