# 📚 SMS (School Management System) - Web Application
![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)

## 📖 About the Project

This is a Java Spring Boot based Web Application where Thymeleaf has been used for the Frontend and PostgreSQL has been used as the Database.

---

## ⚙️ System Requirements

Before running the project, the following software must be installed on your computer:

| Software           | Version      | Download Link                                                        |
| ------------------ | ------------ | -------------------------------------------------------------------- |
| **Java JDK**       | 17  | [Download Java](https://www.oracle.com/java/technologies/downloads/) |
| **Maven**          | 3.8+         | [Download Maven](https://maven.apache.org/download.cgi)              |
| **PostgreSQL**     | 14+          | [Download PostgreSQL](https://www.postgresql.org/download/)          |
| **IDE** (Optional) | -            | IntelliJ IDEA / Eclipse / VS Code                                    |

---

## 🚀 Installation Step by Step

### Step 1: Install Java JDK

#### Windows:

1. Download Java JDK 17+ from [Oracle Java Download](https://www.oracle.com/java/technologies/downloads/)
2. Click the `.exe` file and install
3. Set Environment Variable:
    - `JAVA_HOME` = `C:\Program Files\Java\jdk-17` (according to your installation path)
    - Add to `Path` :  `%JAVA_HOME%\bin`

#### Linux (Ubuntu/Debian):

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

#### Verification:

```bash
java -version
```

---

### Step 2: Install Maven

#### Windows:

1. Download Binary zip archive from [Maven Download](https://maven.apache.org/download.cgi)
2. Extract the file (example: `C:\Program Files\Apache\maven`)
3. Set Environment Variable:
    - `MAVEN_HOME` = `C:\Program Files\Apache\maven`
    - Add to `Path`: `%MAVEN_HOME%\bin`

#### Linux (Ubuntu):

```bash
sudo apt update
sudo apt install maven
```

#### Verification:

```bash
mvn -version
```

---

### Step 3: Install PostgreSQL

#### Windows:

1. Download from [PostgreSQL Download](https://www.postgresql.org/download/windows/)
2. Run the installer and remember the following information:
    - **Password**: Set a strong password (will be needed later)
    - **Port**: Keep default `5432`
    - **Locale**: Keep default

#### Linux (Ubuntu):

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

#### Verification:

```bash
psql --version
```

---

### Step 4: Create Database

1. Open PostgreSQL Command Line or pgAdmin

```bash
# Login to PostgreSQL
psql -U postgres

# Create Database
CREATE DATABASE sms;

# Exit
\q
```

---

### Step 5: Clone the Project

```bash
# Clone with Git
git clone https://github.com/aljami24/sms.git

# Go to project folder
cd sms
```

Or download the ZIP file and Extract it.

---

### Step 6: Configure

In the project’s `src/main/resources/application.properties` file, change the following configuration:

```properties
# Database Configuration
#=======================
spring.application.name=smha SMS
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.main.banner-mode=off
logging.level.org.springframework=info

# File Upload Directory
#=======================
# Use your local absolute path

# Windows example:
# file.upload-directory=C:/sms/uploads

# Linux / Mac example:
# file.upload-directory=/home/user/sms/uploads

file.upload-directory=C:/sms/uploads


# Liquibase
#========================
spring.liquibase.enabled=true
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yml
spring.liquibase.default-schema=public

# Environment Variable
#========================
DB_HOST=localhost;
DB_PORT=5432;
DB_NAME=sms;
DB_USERNAME=;   (enter your db username)
DB_PASSWORD=   (enter your db password)
```
---

### Step 7: Run the Project

#### Run with Maven

```bash
# Build the project
mvn clean install

# Run the project
mvn spring-boot:run
```

### Step 8: Access the Application

Go to the Browser:

```
http://localhost:8080
```

Your application is running! 🎉

## Step 9: 🔐 Development Accounts (Local Only)

⚠️ These accounts are for **local development/demo only**.

| Role       | Username | Password |
| ---------- | -------- | -------- |
| Admin      | admin    | 123      |
| Registrar  | register | 123      |
| Accountant | account  | 123      |