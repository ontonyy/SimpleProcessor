# Rebuild service image, because of changes handling
docker build -t simple-processor ../

# Clean up runned docker containers
docker-compose down

# Run multi-containers with necessary services
docker-compose up -d