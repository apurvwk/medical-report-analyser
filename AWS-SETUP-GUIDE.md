# 🚀 Automated AWS Deployment Setup Guide

This guide will help you set up **fully automated deployment** to AWS EC2 using GitHub Actions and Terraform.

## 📋 Prerequisites

1. **AWS Account** with free tier access
2. **GitHub Repository** with your code
3. **AWS CLI** (optional, for local testing)

## 🔑 Step 1: Create AWS IAM User

### 1.1 Go to AWS IAM Console
- Navigate to **IAM** → **Users** → **Create User**
- Name: `github-actions-deployer`

### 1.2 Attach Permissions
Attach this policy (or create a custom one with minimal permissions):

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "ec2:*",
                "iam:CreateAccessKey",
                "iam:DeleteAccessKey",
                "iam:ListAccessKeys",
                "iam:UpdateAccessKey"
            ],
            "Resource": "*"
        }
    ]
}
```

### 1.3 Create Access Keys
- Go to **Security credentials** tab
- Click **Create access key**
- Choose **Command Line Interface (CLI)**
- **Copy both Access Key ID and Secret Access Key**

## 🔐 Step 2: Set Up GitHub Secrets

Go to your GitHub repository → **Settings** → **Secrets and variables** → **Actions**

Add these secrets:

| Secret Name | Value |
|-------------|-------|
| `AWS_ACCESS_KEY_ID` | Your AWS Access Key ID |
| `AWS_SECRET_ACCESS_KEY` | Your AWS Secret Access Key |
| `AWS_SSH_PRIVATE_KEY` | (Will be generated automatically) |

## 🏗️ Step 3: Create Infrastructure

### 3.1 Generate SSH Key (if you don't have one)
```bash
ssh-keygen -t rsa -b 4096 -f ~/.ssh/github-actions -N ""
```

### 3.2 Update Terraform Configuration
Edit `terraform/main.tf` and update the SSH key path:
```hcl
resource "aws_key_pair" "main" {
  key_name   = "pdf-processor-key"
  public_key = file("~/.ssh/github-actions.pub")  # Update this path
}
```

### 3.3 Trigger Infrastructure Creation
1. Go to your GitHub repository
2. Navigate to **Actions** tab
3. Select **"Setup AWS Infrastructure"** workflow
4. Click **"Run workflow"**
5. Select your branch and click **"Run workflow"**

### 3.4 Get Infrastructure Outputs
After the workflow completes, you'll see outputs like:
```
Public IP: 52.23.45.67
Instance ID: i-1234567890abcdef0
```

## 🔧 Step 4: Complete GitHub Secrets Setup

After infrastructure is created, add these additional secrets:

| Secret Name | Value |
|-------------|-------|
| `AWS_EC2_HOST` | The Public IP from Terraform output |
| `AWS_EC2_USER` | `ec2-user` |
| `AWS_EC2_SSH_KEY` | Your private SSH key content |

To get your private key content:
```bash
cat ~/.ssh/github-actions
```

## 🚀 Step 5: Test Automated Deployment

### 5.1 Push Code Changes
```bash
git add .
git commit -m "Add automated AWS deployment"
git push origin main
```

### 5.2 Monitor Deployment
1. Go to **Actions** tab in GitHub
2. Watch the **"Build and Push Multi-Arch Docker Image"** workflow
3. After it completes, watch the **"Deploy to AWS EC2"** workflow

### 5.3 Access Your Application
Your application will be available at:
```
http://YOUR_EC2_PUBLIC_IP:8080
```

## 🔄 How It Works

### Automated Pipeline:
1. **Code Push** → Triggers build workflow
2. **Build & Test** → Runs Maven tests
3. **Docker Build** → Creates multi-arch image
4. **Push to Hub** → Uploads to Docker Hub
5. **Deploy to AWS** → Pulls and runs on EC2
6. **Health Check** → Verifies deployment

### Infrastructure (Terraform):
- ✅ **VPC & Subnet** - Network isolation
- ✅ **Security Group** - Firewall rules
- ✅ **EC2 Instance** - t2.micro (free tier)
- ✅ **Key Pair** - SSH access
- ✅ **Auto Docker Setup** - User data script

## 💰 Cost Estimation (Free Tier)

| Service | Free Tier | Monthly Cost |
|---------|-----------|--------------|
| EC2 t2.micro | 750 hours | $0 |
| Data Transfer | 1GB | $0 |
| EBS Storage | 30GB | $0 |
| **Total** | | **$0** |

## 🛠️ Troubleshooting

### Common Issues:

1. **SSH Connection Failed**
   - Check security group allows port 22
   - Verify SSH key is correct
   - Wait for instance to fully boot

2. **Docker Not Running**
   - Instance user data script may need time
   - Check Docker service status
   - Verify user is in docker group

3. **Application Not Accessible**
   - Check security group allows port 8080
   - Verify container is running
   - Check application logs

### Debug Commands:
```bash
# Check instance status
aws ec2 describe-instances --instance-ids i-1234567890abcdef0

# Check security group
aws ec2 describe-security-groups --group-ids sg-1234567890abcdef0

# SSH to instance
ssh -i ~/.ssh/github-actions ec2-user@YOUR_EC2_IP
```

## 🎉 Success!

Once everything is set up, every push to `main` will automatically:
1. ✅ Build your Docker image
2. ✅ Push to Docker Hub
3. ✅ Deploy to AWS EC2
4. ✅ Verify deployment health

Your application will be live and accessible from anywhere! 🌐 