# Docker Hub Setup Guide

## Why Docker Hub?

✅ **Free for public repositories**  
✅ **No setup required**  
✅ **Widely supported**  
✅ **Easy to use**  
✅ **Perfect for open source projects**

## Step 1: Create Docker Hub Account

1. Go to [Docker Hub](https://hub.docker.com/)
2. Click "Sign Up"
3. Create your account with username, email, and password
4. Verify your email address

## Step 2: Create Access Token

1. Login to Docker Hub
2. Go to Account Settings → Security
3. Click "New Access Token"
4. Give it a name (e.g., "GitHub Actions")
5. Select "Read & Write" permissions
6. Copy the generated token (you won't see it again!)

## Step 3: Configure GitHub Secrets

In your GitHub repository:

1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add these secrets:

```
DOCKERHUB_USERNAME: your-dockerhub-username
DOCKERHUB_TOKEN: your-access-token-from-step-2
```

## Step 4: Update Workflow Configuration

Update `.github/workflows/build-and-push.yml`:

```yaml
env:
  REGISTRY: docker.io
  IMAGE_NAME: your-dockerhub-username/pdf-processor  # Replace with your username
```

## Step 5: Test Your Setup

### Option A: Test Locally
```bash
# Build your image
docker build -t pdf-processor .

# Tag for Docker Hub
docker tag pdf-processor your-dockerhub-username/pdf-processor:latest

# Login to Docker Hub
docker login

# Push to Docker Hub
docker push your-dockerhub-username/pdf-processor:latest
```

### Option B: Test with GitHub Actions
1. Push your changes to GitHub
2. Check the Actions tab
3. Verify the workflow runs successfully
4. Check your Docker Hub repository for the pushed image

## Docker Hub Repository Structure

Your images will be available at:
- `docker.io/your-username/pdf-processor:latest`
- `docker.io/your-username/pdf-processor:main`
- `docker.io/your-username/pdf-processor:v1.0.0` (for tags)

## Pulling Your Images

```bash
# Pull the latest version
docker pull your-dockerhub-username/pdf-processor:latest

# Run the container
docker run -p 8080:8080 your-dockerhub-username/pdf-processor:latest
```

## Docker Hub Limitations

### Free Tier Limits:
- **Public repositories**: Unlimited
- **Private repositories**: 1 (with limitations)
- **Image pulls**: 200 per 6 hours for anonymous users
- **Storage**: 1GB for free tier

### For Production Use:
- Consider upgrading to paid plan for more private repos
- Or use GitHub Container Registry (free for public repos)

## Alternative: GitHub Container Registry

If you prefer to keep everything in GitHub:

1. Update workflow to use `ghcr.io`:
```yaml
env:
  REGISTRY: ghcr.io
  IMAGE_NAME: your-github-username/pdf-processor
```

2. Use `GITHUB_TOKEN` instead of Docker Hub credentials

## Troubleshooting

### Common Issues:

1. **Authentication Failed**
   - Check your Docker Hub username and token
   - Ensure token has correct permissions

2. **Repository Not Found**
   - Verify your Docker Hub username in the workflow
   - Check if the repository exists

3. **Rate Limiting**
   - Free tier has pull limits
   - Consider upgrading for production use

### Support:
- [Docker Hub Documentation](https://docs.docker.com/docker-hub/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)

## Next Steps

1. ✅ Set up Docker Hub account
2. ✅ Create access token
3. ✅ Configure GitHub secrets
4. ✅ Update workflow file
5. ✅ Test the pipeline
6. 🎉 Your images are now automatically published!

Your Spring Boot application will now be automatically built and pushed to Docker Hub on every push to main/develop branches! 