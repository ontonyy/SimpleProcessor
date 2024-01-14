# Clean up runned docker containers
docker-compose down

# Rebuild service image, because of changes handling
cd .. && ./gradlew clean build --parallel
docker build -t simple-processor .

# Run multi-containers with necessary services
cd configs && docker-compose up