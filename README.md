# Job Portal API 🚀

A full-featured **Job Portal REST API** built with **Spring Boot**, demonstrating advanced backend development concepts including JWT authentication, role-based access control, file uploads, email notifications, and scheduled tasks.

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17+ |
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT (jjwt 0.12.x) |
| Database | MySQL + Spring Data JPA / Hibernate |
| File Storage | Local file system (upload-dir) |
| Email | Spring Mail (Gmail SMTP) |
| Build Tool | Maven |
| Testing | Postman |

---

## 🏗 Architecture

Three-layer architecture with a dedicated Security layer for JWT filtering.

    src/main/java/com/likithbabugarlapati/jobportalapi/
    ├── Config/           # SecurityConfig, JwtAuthFilter
    ├── Controller/       # REST endpoints
    ├── Service/          # Business logic
    ├── Repository/       # JPA repositories
    ├── Model/            # JPA entities
    ├── dto/              # Request/Response DTOs
    ├── enums/            # Role, JobStatus, ApplicationStatus
    └── security/         # JwtService, CustomUserDetailsService

---

## ✨ Features

### 🔐 Authentication & Authorization
- JWT-based stateless authentication
- Role-based access control: `APPLICANT`, `RECRUITER`, `ADMIN`
- BCrypt password encoding
- Method-level security with `@PreAuthorize`

### 💼 Job Management
- Recruiters post, update, and delete jobs
- Applicants browse and search open jobs
- Advanced search by title, location, job type, salary range
- Job expiry dates with auto-close scheduler (runs daily at midnight)

### 📋 Applications
- Applicants apply to jobs with PDF resume upload
- Duplicate application prevention
- Recruiters view and update application status (PENDING → REVIEWED → ACCEPTED/REJECTED)

### 📧 Email Notifications
- Automatic email to applicant when application status changes
- Handles ACCEPTED, REJECTED, and REVIEWED states

### 🏢 Company Profiles
- Recruiters create and manage a company profile
- Company info linked to job listings

### 🔖 Saved Jobs
- Applicants bookmark jobs to apply later
- Toggle save/unsave with a single endpoint

### 📊 Dashboards
- **Admin**: total users, jobs, applications, companies
- **Recruiter**: jobs posted, applications received per job
- **Applicant**: application history with status breakdown

---

## 🗄 Database Schema

    users        → id, name, email, password, role, created_at
    job          → id, title, description, location, job_type, salary_min, salary_max, status, expiry_date, posted_by (FK), created_at
    application  → id, job_id (FK), applicant_id (FK), resume_path, status, applied_at
    company      → id, name, description, website, location, industry, recruiter_id (FK), created_at
    saved_jobs   → id, applicant_id (FK), job_id (FK), saved_at
    notification → id, user_id (FK), message, is_read, created_at

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- MySQL 8+
- Maven

### 1. Clone the repository

    git clone https://github.com/LikithBabu/job-portal-api.git
    cd job-portal-api

### 2. Configure application.properties
Copy the example file and fill in your values:

    cp src/main/resources/application.properties.example src/main/resources/application.properties

### 3. Run the application

    mvn spring-boot:run

App starts at `http://localhost:8081`

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| POST | `/api/auth/register` | Public | Register user |
| POST | `/api/auth/login` | Public | Login & get JWT |

### Jobs
| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| POST | `/api/jobs` | RECRUITER | Create job |
| GET | `/api/jobs` | All | Get open jobs |
| GET | `/api/jobs/search` | All | Search jobs |
| GET | `/api/jobs/{id}` | All | Get job by ID |
| PUT | `/api/jobs/{id}` | RECRUITER | Update job |
| DELETE | `/api/jobs/{id}` | RECRUITER | Delete job |
| GET | `/api/jobs/my-jobs` | RECRUITER | My posted jobs |
| GET | `/api/jobs/all` | ADMIN | All jobs |

### Applications
| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| POST | `/api/applications/apply/{jobId}` | APPLICANT | Apply with resume |
| GET | `/api/applications/my-applications` | APPLICANT | My applications |
| GET | `/api/applications/job/{jobId}` | RECRUITER | Applications for job |
| PATCH | `/api/applications/{id}/status` | RECRUITER | Update status |

### Companies
| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| POST | `/api/companies` | RECRUITER | Create company |
| GET | `/api/companies/my-company` | RECRUITER | My company |
| PUT | `/api/companies/my-company` | RECRUITER | Update company |
| GET | `/api/companies` | All | All companies |
| GET | `/api/companies/{id}` | All | Company by ID |

### Saved Jobs
| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| POST | `/api/saved-jobs/{jobId}` | APPLICANT | Save/unsave toggle |
| GET | `/api/saved-jobs` | APPLICANT | My saved jobs |
| GET | `/api/saved-jobs/{jobId}/is-saved` | APPLICANT | Check if saved |

### Dashboard
| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| GET | `/api/dashboard/admin` | ADMIN | Admin stats |
| GET | `/api/dashboard/recruiter` | RECRUITER | Recruiter stats |
| GET | `/api/dashboard/applicant` | APPLICANT | Applicant stats |

---

## 🔒 Authentication

All protected endpoints require a Bearer token:

    Authorization: Bearer <your_jwt_token>

Get a token by calling `POST /api/auth/login`.

---

## 🔑 Key Design Decisions

- **Stateless JWT** — No server-side session; scales horizontally
- **Role-based method security** — `@PreAuthorize` at controller level keeps business logic clean
- **Graceful email failure** — Status update succeeds even if email fails
- **Toggle bookmark** — Single endpoint handles both save and unsave
- **Scheduler** — `@Scheduled(cron = "0 0 0 * * *")` auto-closes expired jobs at midnight

---

## 👨‍💻 Author

**Likith Babu Garlapati**
[LinkedIn](https://linkedin.com/in/likith-babu-garlapati-18a401277) · [GitHub](https://github.com/LikithBabu)
