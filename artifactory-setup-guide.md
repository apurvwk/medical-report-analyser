# Artifactory Setup Guide

## Option 1: JFrog Cloud (Recommended for Beginners)

### Step 1: Sign Up
1. Visit [JFrog Cloud](https://jfrog.com/start-free/)
2. Click "Start Free Trial"
3. Fill in your details and create an account

### Step 2: Create Instance
1. Choose "Artifactory" as your product
2. Select your cloud provider (AWS, Azure, GCP)
3. Choose your preferred region
4. Select instance size (start with small for testing)
5. Click "Create Instance"

### Step 3: Access Your Instance
1. Wait for instance to be ready (5-10 minutes)
2. Access via the provided URL (e.g., `https://your-instance.jfrog.io`)
3. Login with your credentials

## Option 2: Local Docker Setup (Development)

### Prerequisites
- Docker and Docker Compose installed

### Quick Start
```bash
# Start Artifactory locally
docker-compose -f docker-compose-artifactory.yml up -d

# Access Artifactory
# URL: http://localhost:8081
# Default credentials: admin/password
```

### Initial Setup
1. Open http://localhost:8081
2. Login with admin/password
3. Change default password when prompted
4. Create your first repository

## Option 3: Self-Hosted Production

### Using Helm (Kubernetes)
```bash
# Add JFrog Helm repository
helm repo add jfrog https://charts.jfrog.io
helm repo update

# Install Artifactory
helm install artifactory jfrog/artifactory \
  --set artifactory.persistence.size=10Gi \
  --set artifactory.service.type=LoadBalancer
```

## Repository Setup

### 1. Create Docker Repository
1. Go to Artifactory → Repositories
2. Click "Add Repository" → "Docker"
3. Choose repository type:
   - **Local**: For your own images
   - **Remote**: Proxy to Docker Hub
   - **Virtual**: Combine multiple repositories

### 2. Configure Repository
```yaml
# Example: Local Docker repository
Repository Key: docker-local
Repository Type: Local
Package Type: Docker
```

### 3. Get Repository URL
Your repository URL will be:
- **Cloud**: `https://your-instance.jfrog.io/artifactory/docker-local`
- **Local**: `http://localhost:8081/artifactory/docker-local`

## Authentication Setup

### 1. Create API Key
1. Go to Artifactory → User Profile
2. Click "Generate API Key"
3. Copy the generated key

### 2. Update GitHub Secrets
In your GitHub repository → Settings → Secrets:
- `ARTIFACTORY_USERNAME`: Your username
- `ARTIFACTORY_API_KEY`: Your API key

### 3. Update Workflow Configuration
Update `.github/workflows/build-and-push.yml`:
```yaml
env:
  REGISTRY: your-instance.jfrog.io  # Replace with your URL
  IMAGE_NAME: pdf-processor
```

## Testing Your Setup

### 1. Test Local Build
```bash
# Build your image locally
docker build -t pdf-processor .

# Tag for your Artifactory
docker tag pdf-processor your-instance.jfrog.io/docker-local/pdf-processor:latest

# Login to Artifactory
docker login your-instance.jfrog.io

# Push to Artifactory
docker push your-instance.jfrog.io/docker-local/pdf-processor:latest
```

### 2. Test GitHub Actions
1. Push your changes to GitHub
2. Check the Actions tab
3. Verify the workflow runs successfully
4. Check Artifactory for the pushed image

## Troubleshooting

### Common Issues
1. **Authentication Failed**: Check API key and username
2. **Repository Not Found**: Ensure repository exists and is accessible
3. **Network Issues**: Verify firewall settings and network connectivity

### Support Resources
- [JFrog Documentation](https://www.jfrog.com/confluence/)
- [Artifactory User Guide](https://www.jfrog.com/confluence/display/JFROG/Artifactory+User+Guide)
- [Community Forums](https://community.jfrog.com/) 