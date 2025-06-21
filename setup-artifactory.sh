#!/bin/bash

echo "🚀 Artifactory Setup Script"
echo "=========================="

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

echo "✅ Docker and Docker Compose are installed"

# Create docker-compose file if it doesn't exist
if [ ! -f "docker-compose-artifactory.yml" ]; then
    echo "❌ docker-compose-artifactory.yml not found. Please ensure it exists."
    exit 1
fi

echo "📦 Starting Artifactory..."
docker-compose -f docker-compose-artifactory.yml up -d

echo "⏳ Waiting for Artifactory to start..."
sleep 30

# Check if Artifactory is running
if curl -s http://localhost:8081 > /dev/null; then
    echo "✅ Artifactory is running!"
    echo ""
    echo "🌐 Access Artifactory at: http://localhost:8081"
    echo "👤 Default credentials: admin / password"
    echo ""
    echo "📋 Next steps:"
    echo "1. Open http://localhost:8081 in your browser"
    echo "2. Login with admin/password"
    echo "3. Change the default password"
    echo "4. Create a Docker repository (docker-local)"
    echo "5. Generate an API key in your user profile"
    echo "6. Update your GitHub secrets with the API key"
    echo ""
    echo "🔧 To stop Artifactory:"
    echo "   docker-compose -f docker-compose-artifactory.yml down"
else
    echo "❌ Artifactory failed to start. Check the logs:"
    echo "   docker-compose -f docker-compose-artifactory.yml logs"
fi 