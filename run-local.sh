#!/bin/bash

echo "🚀 PDF Processor - Local Docker Runner"
echo "======================================"

# Configuration
IMAGE_NAME="apurvwk/medical-report-analyser"
CONTAINER_NAME="pdf-processor"
HOST_PORT="8080"
CONTAINER_PORT="8080"

# Function to check if container is running
is_container_running() {
    docker ps --format "table {{.Names}}" | grep -q "^${CONTAINER_NAME}$"
}

# Function to check if container exists
container_exists() {
    docker ps -a --format "table {{.Names}}" | grep -q "^${CONTAINER_NAME}$"
}

# Stop and remove existing container
if is_container_running; then
    echo "🛑 Stopping existing container..."
    docker stop ${CONTAINER_NAME}
fi

if container_exists; then
    echo "🗑️  Removing existing container..."
    docker rm ${CONTAINER_NAME}
fi

# Pull latest image
echo "📥 Pulling latest image..."
docker pull ${IMAGE_NAME}:latest

# Create PDF files directory
echo "📁 Creating PDF files directory..."
mkdir -p ~/pdf-files

# Run the container
echo "🚀 Starting container..."
docker run -d \
    --name ${CONTAINER_NAME} \
    -p ${HOST_PORT}:${CONTAINER_PORT} \
    -v ~/pdf-files:/app/pdfs \
    -e SPRING_PROFILES_ACTIVE=dev \
    ${IMAGE_NAME}:latest

# Wait for container to start
echo "⏳ Waiting for application to start..."
sleep 10

# Check if container is running
if is_container_running; then
    echo "✅ Container is running!"
    echo ""
    echo "🌐 Access your application at:"
    echo "   http://localhost:${HOST_PORT}"
    echo ""
    echo "📁 PDF files directory: ~/pdf-files"
    echo ""
    echo "📋 Useful commands:"
    echo "   View logs: docker logs ${CONTAINER_NAME}"
    echo "   Stop container: docker stop ${CONTAINER_NAME}"
    echo "   Remove container: docker rm ${CONTAINER_NAME}"
    echo "   Shell access: docker exec -it ${CONTAINER_NAME} /bin/sh"
    echo ""
    echo "🔍 Testing health endpoint..."
    if curl -s http://localhost:${HOST_PORT}/actuator/health > /dev/null; then
        echo "✅ Application is healthy!"
    else
        echo "⚠️  Health check failed. Check logs with: docker logs ${CONTAINER_NAME}"
    fi
else
    echo "❌ Container failed to start. Check logs:"
    docker logs ${CONTAINER_NAME}
fi 