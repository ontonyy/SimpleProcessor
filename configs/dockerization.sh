# Clean up runned docker containers
docker-compose down

# Rebuild service image, because of changes handling
docker build -t simple-processor ../

# Run multi-containers with necessary services
docker-compose up -d