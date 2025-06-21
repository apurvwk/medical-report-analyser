# PDF Processor - Medical Report Analyzer

A Spring Boot application for processing and analyzing PDF medical reports using Apache PDFBox.

## Features

- PDF text extraction and processing
- Medical report analysis capabilities
- RESTful API endpoints
- Docker containerization support
- Automated CI/CD pipeline with GitHub Actions

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.3**
- **Apache PDFBox 2.0.29**
- **Maven**
- **Docker**
- **GitHub Actions**

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker (for containerized deployment)

## Local Development

### Building the Application

```bash
# Clone the repository
git clone <your-repo-url>
cd medical-report-analyser

# Build the application
mvn clean package

# Run the application
java -jar target/pdf-processor-0.0.1-SNAPSHOT.jar
```

### Running with Docker

```bash
# Build the Docker image
docker build -t pdf-processor .

# Run the container
docker run -p 8080:8080 pdf-processor
```

## CI/CD Pipeline

This project includes a GitHub Actions workflow that automatically:

1. **Builds and Tests**: Compiles the code and runs unit tests
2. **Creates Docker Image**: Builds a multi-stage Docker image
3. **Pushes to Docker Hub**: Deploys the image to Docker Hub registry

### Workflow Triggers

- **Push to main/develop branches**: Builds and pushes the image
- **Pull Requests**: Runs tests only
- **Tags (v* pattern)**: Creates versioned releases

### Required GitHub Secrets

To use the CI/CD pipeline, you need to configure these secrets in your GitHub repository:

1. **DOCKERHUB_USERNAME**: Your Docker Hub username
2. **DOCKERHUB_TOKEN**: Your Docker Hub access token

### Configuration

Update the following in `.github/workflows/build-and-push.yml`:

```yaml
env:
  REGISTRY: docker.io
  IMAGE_NAME: your-dockerhub-username/pdf-processor  # Replace with your username
```

### Docker Hub Setup

1. **Create Docker Hub Account**: Sign up at [hub.docker.com](https://hub.docker.com/)
2. **Generate Access Token**: Go to Account Settings → Security → New Access Token
3. **Configure GitHub Secrets**: Add your username and token to repository secrets
4. **Update Workflow**: Replace `your-dockerhub-username` with your actual username

For detailed setup instructions, see [dockerhub-setup-guide.md](dockerhub-setup-guide.md).

## Docker Image Features

- **Multi-stage build** for optimized image size
- **Security best practices** with non-root user
- **Health checks** for container monitoring
- **Layer caching** for faster builds
- **Alpine Linux base** for minimal footprint

## API Endpoints

The application exposes REST endpoints for PDF processing (endpoints to be implemented based on your specific requirements).

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

[Add your license information here] 