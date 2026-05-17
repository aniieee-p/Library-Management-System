# Deployment Guide - Smart Library Management System

This guide covers deploying your Spring Boot application to production.

## 🚀 Live Deployment

**Production URL:** [https://library-management-system-production-a2c7.up.railway.app/](https://library-management-system-production-a2c7.up.railway.app/)

Deployed on Railway with MySQL database.

## 📋 Table of Contents
1. [Local Deployment](#local-deployment)
2. [Creating Production JAR](#creating-production-jar)
3. [Cloud Deployment (Railway)](#cloud-deployment-railway)
4. [Database Setup](#database-setup)
5. [Environment Configuration](#environment-configuration)
6. [Troubleshooting](#troubleshooting)

---

## 🏠 Local Deployment

### Prerequisites
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### Steps

1. **Create Database**
```sql
CREATE DATABASE library_db;
```

2. **Configure Application**  
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=your_password
```

3. **Run Application**
```bash
mvn clean install
mvn spring-boot:run
```

4. **Access**
```
http://localhost:8080
```

---

## 📦 Creating Production JAR

### Build Executable JAR

```bash
# Clean and build
mvn clean package

# JAR file will be created in target/ directory
# File: target/library-management-system-1.0.0.jar
```

### Run JAR File

```bash
java -jar target/library-management-system-1.0.0.jar
```

### Run with Custom Port

```bash
java -jar -Dserver.port=8081 target/library-management-system-1.0.0.jar
```

### Run with External Configuration

```bash
java -jar target/library-management-system-1.0.0.jar \
  --spring.datasource.url=jdbc:mysql://your-db-host:3306/library_db \
  --spring.datasource.username=your_username \
  --spring.datasource.password=your_password
```

---

## ☁️ Cloud Deployment (Render)

### Option 1: Deploy to Render (Free Tier)

Render provides free hosting for web services and databases.

#### Step 1: Prepare Your Application

1. **Create `application-prod.properties`**

Create `src/main/resources/application-prod.properties`:

```properties
# Server Configuration
server.port=${PORT:8080}

# Database Configuration (will use environment variables)
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Disable SQL initialization in production
spring.sql.init.mode=never

# Logging
logging.level.root=INFO
logging.level.com.library=INFO
```

2. **Update Main Application Properties**

Update `src/main/resources/application.properties`:

```properties
# Application Configuration
spring.application.name=library-management-system
server.port=${PORT:8080}

# Database Configuration
spring.datasource.url=${DATABASE_URL:jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:root}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Initialize database with schema and data (only for local)
spring.sql.init.mode=${INIT_MODE:always}
spring.sql.init.continue-on-error=true

# Logging Configuration
logging.level.root=INFO
logging.level.com.library=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG
logging.file.name=logs/application.log

# Server Configuration
server.error.include-message=always
server.error.include-binding-errors=always

# Jackson Configuration
spring.jackson.serialization.fail-on-empty-beans=false
```

3. **Create Build Script**

Create `build.sh` in project root:

```bash
#!/bin/bash
mvn clean package -DskipTests
```

Make it executable:
```bash
chmod +x build.sh
```

4. **Create Start Script**

Create `start.sh` in project root:

```bash
#!/bin/bash
java -jar target/library-management-system-1.0.0.jar
```

Make it executable:
```bash
chmod +x start.sh
```

#### Step 2: Setup MySQL Database on Render

1. **Go to Render Dashboard**
   - Visit https://render.com
   - Sign up / Login

2. **Create MySQL Database**
   - Click "New +" → "MySQL"
   - Name: `library-db`
   - Database: `library_db`
   - User: `library_user`
   - Region: Choose closest to you
   - Plan: Free
   - Click "Create Database"

3. **Get Database Credentials**
   - After creation, note down:
     - Internal Database URL
     - Username
     - Password
     - Database Name

4. **Initialize Database**
   - Connect to database using provided credentials
   - Run `schema.sql` to create tables
   - Run `data.sql` to insert sample data

   Using MySQL client:
   ```bash
   mysql -h <hostname> -u <username> -p<password> <database_name> < src/main/resources/schema.sql
   mysql -h <hostname> -u <username> -p<password> <database_name> < src/main/resources/data.sql
   ```

#### Step 3: Deploy Application to Render

1. **Push Code to GitHub**
```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin <your-github-repo-url>
git push -u origin main
```

2. **Create Web Service on Render**
   - Click "New +" → "Web Service"
   - Connect your GitHub repository
   - Configure:
     - **Name**: `library-management-system`
     - **Region**: Same as database
     - **Branch**: `main`
     - **Root Directory**: Leave empty
     - **Runtime**: `Java`
     - **Build Command**: `./build.sh`
     - **Start Command**: `./start.sh`
     - **Plan**: Free

3. **Add Environment Variables**
   - In "Environment" section, add:
   
   ```
   DATABASE_URL=jdbc:mysql://<db-host>:<port>/library_db
   DB_USERNAME=<your-db-username>
   DB_PASSWORD=<your-db-password>
   PORT=8080
   INIT_MODE=never
   SPRING_PROFILES_ACTIVE=prod
   ```

4. **Deploy**
   - Click "Create Web Service"
   - Wait for deployment (5-10 minutes)
   - Your app will be available at: `https://your-app-name.onrender.com`

#### Step 4: Update Frontend URLs

Update API URLs in your frontend files to point to deployed backend:

In `static/js/main.js` and HTML files, change:
```javascript
// From
const API_URL = 'http://localhost:8080/api';

// To
const API_URL = 'https://your-app-name.onrender.com/api';
```

Or use environment-based URL:
```javascript
const API_URL = window.location.hostname === 'localhost' 
  ? 'http://localhost:8080/api'
  : 'https://your-app-name.onrender.com/api';
```

---

### Option 2: Deploy to Railway (Alternative)

Railway is another free hosting option.

1. **Visit Railway**
   - Go to https://railway.app
   - Sign up with GitHub

2. **Create New Project**
   - Click "New Project"
   - Select "Deploy from GitHub repo"
   - Choose your repository

3. **Add MySQL Database**
   - Click "New" → "Database" → "MySQL"
   - Railway will provide connection details

4. **Configure Environment Variables**
   ```
   DATABASE_URL=<railway-mysql-url>
   DB_USERNAME=<username>
   DB_PASSWORD=<password>
   PORT=8080
   ```

5. **Deploy**
   - Railway auto-deploys on git push
   - Access at provided URL

---

### Option 3: Deploy to Heroku (Alternative)

1. **Install Heroku CLI**
```bash
# Download from https://devcenter.heroku.com/articles/heroku-cli
```

2. **Login to Heroku**
```bash
heroku login
```

3. **Create Heroku App**
```bash
heroku create library-management-app
```

4. **Add MySQL Database**
```bash
heroku addons:create jawsdb:kitefin
```

5. **Get Database URL**
```bash
heroku config:get JAWSDB_URL
```

6. **Set Environment Variables**
```bash
heroku config:set DATABASE_URL=<your-jawsdb-url>
heroku config:set SPRING_PROFILES_ACTIVE=prod
```

7. **Deploy**
```bash
git push heroku main
```

8. **Open App**
```bash
heroku open
```

---

## 🗄️ Database Setup

### Local MySQL Setup

```sql
-- Create database
CREATE DATABASE library_db;

-- Use database
USE library_db;

-- Run schema
SOURCE /path/to/schema.sql;

-- Run data
SOURCE /path/to/data.sql;

-- Verify
SHOW TABLES;
SELECT COUNT(*) FROM books;
SELECT COUNT(*) FROM users;
```

### Cloud MySQL Setup

For cloud deployment, you have options:

1. **Render MySQL** (Free tier available)
2. **Railway MySQL** (Free tier available)
3. **AWS RDS** (Free tier for 12 months)
4. **Google Cloud SQL** (Free tier available)
5. **Azure Database** (Free tier available)

---

## ⚙️ Environment Configuration

### Development (Local)

`application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
logging.level.com.library=DEBUG
```

### Production (Cloud)

`application-prod.properties`:
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=never
logging.level.com.library=INFO
```

### Using Profiles

Run with specific profile:
```bash
# Development
mvn spring-boot:run

# Production
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Or with JAR
java -jar -Dspring.profiles.active=prod target/library-management-system-1.0.0.jar
```

---

## 🔧 Troubleshooting

### Issue 1: Application Won't Start

**Error**: `Port 8080 already in use`

**Solution**:
```bash
# Change port
java -jar -Dserver.port=8081 target/library-management-system-1.0.0.jar
```

---

### Issue 2: Database Connection Failed

**Error**: `Unable to connect to database`

**Solutions**:

1. **Check MySQL is running**
```bash
# Windows
net start MySQL80

# Linux
sudo systemctl start mysql

# Mac
brew services start mysql
```

2. **Verify credentials**
```bash
mysql -u root -p
# Enter password and check if you can connect
```

3. **Check database exists**
```sql
SHOW DATABASES;
```

4. **Check connection string**
```properties
# Correct format
spring.datasource.url=jdbc:mysql://localhost:3306/library_db

# With timezone
spring.datasource.url=jdbc:mysql://localhost:3306/library_db?serverTimezone=UTC
```

---

### Issue 3: Tables Not Created

**Solution**:

1. **Check ddl-auto setting**
```properties
spring.jpa.hibernate.ddl-auto=update
```

2. **Manually run schema**
```bash
mysql -u root -p library_db < src/main/resources/schema.sql
```

---

### Issue 4: Sample Data Not Loading

**Solution**:

1. **Check init mode**
```properties
spring.sql.init.mode=always
```

2. **Manually run data script**
```bash
mysql -u root -p library_db < src/main/resources/data.sql
```

---

### Issue 5: CORS Errors in Production

**Error**: `Access to XMLHttpRequest blocked by CORS policy`

**Solution**:

Update `WebConfig.java`:
```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:8080",
                "https://your-app-name.onrender.com"
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
}
```

---

### Issue 6: Build Fails on Render

**Error**: `Build failed`

**Solutions**:

1. **Check Java version**
   - Ensure `pom.xml` has Java 17
   - Render uses Java 17 by default

2. **Check build script**
```bash
#!/bin/bash
mvn clean package -DskipTests
```

3. **Check Maven wrapper**
```bash
# Use Maven wrapper if available
./mvnw clean package -DskipTests
```

---

### Issue 7: Application Crashes After Deployment

**Check Logs**:

On Render:
- Go to your service
- Click "Logs" tab
- Look for errors

Common issues:
- Database connection timeout
- Missing environment variables
- Out of memory (upgrade plan)

---

### Issue 8: Frontend Can't Connect to Backend

**Solution**:

1. **Check API URL in frontend**
```javascript
const API_URL = 'https://your-app-name.onrender.com/api';
```

2. **Check CORS configuration**

3. **Test API directly**
```bash
curl https://your-app-name.onrender.com/api/books
```

---

## 📊 Deployment Checklist

### Before Deployment
- [ ] Code pushed to GitHub
- [ ] Database created on cloud
- [ ] Schema and data loaded
- [ ] Environment variables configured
- [ ] CORS settings updated
- [ ] Frontend URLs updated
- [ ] Build script tested locally

### After Deployment
- [ ] Application accessible
- [ ] Can login with credentials
- [ ] All APIs working
- [ ] Database connected
- [ ] Frontend loads correctly
- [ ] No console errors
- [ ] Test all features

---

## 🌐 Accessing Your Deployed Application

### Render URL Format
```
https://your-app-name.onrender.com
```

### Custom Domain (Optional)
1. Go to Render dashboard
2. Select your service
3. Click "Settings"
4. Add custom domain
5. Update DNS records

---

## 💡 Best Practices

1. **Use Environment Variables**
   - Never hardcode credentials
   - Use different configs for dev/prod

2. **Enable HTTPS**
   - Render provides free SSL
   - Always use HTTPS in production

3. **Monitor Logs**
   - Check logs regularly
   - Set up error alerts

4. **Backup Database**
   - Regular database backups
   - Export data periodically

5. **Update Dependencies**
   - Keep dependencies updated
   - Check for security vulnerabilities

---

## 📝 Summary

### Local Development
```bash
mvn spring-boot:run
```

### Production JAR
```bash
mvn clean package
java -jar target/library-management-system-1.0.0.jar
```

### Cloud Deployment
1. Push to GitHub
2. Create database on Render
3. Create web service on Render
4. Configure environment variables
5. Deploy and test

---

## 🎯 Quick Deploy Commands

```bash
# Build
mvn clean package -DskipTests

# Test locally
java -jar target/library-management-system-1.0.0.jar

# Push to GitHub
git add .
git commit -m "Ready for deployment"
git push origin main

# Render will auto-deploy from GitHub
```

---

## 📞 Support

For deployment issues:
1. Check application logs
2. Verify environment variables
3. Test database connection
4. Check CORS settings
5. Review Render documentation

---

**Your application is now ready for deployment!** 🚀
