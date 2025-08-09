[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/vN5dLYMT)
# DesignPatterns
CertiLearn – E-Learning & Certification System
CertiLearn is a Spring Boot–based e-learning platform designed to simplify the process of online learning, quiz management, and automated certification.
It provides role-based access for students, instructors, and administrators, integrates secure authentication, and delivers certificates instantly after quiz completion.

Features
Role-Based Authentication – Separate interfaces and permissions for students, instructors, and admins using JWT authentication.

Course & Quiz Management – Create, manage, and enroll in courses; auto-generate and evaluate quizzes.

Certificate Generation – Automatically generate PDF certificates upon successful course completion.

Responsive Web UI – Optimized front-end for desktops and mobile devices.

Secure REST API – Built using Spring Boot and follows RESTful best practices.

Persistent Data Storage – Uses PostgreSQL for robust and scalable database management.

Tech Stack
Backend: Java, Spring Boot, Spring Security
Database: PostgreSQL
Frontend: HTML, CSS, JavaScript (or applicable framework if integrated)
Build Tool: Maven
Version Control: Git, GitHub

Installation & Setup
Clone the repository

bash
Copy
Edit
git clone https://github.com/Onkar97/CertiLearn-E-Learning-Certification-System.git
cd CertiLearn-E-Learning-Certification-System
Configure the database

Install PostgreSQL and create a database (e.g., certilearn_db).

Update application.properties with your database credentials:

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/certilearn_db
spring.datasource.username=your_username
spring.datasource.password=your_password
Build and run the project

bash
Copy
Edit
mvn clean install
mvn spring-boot:run
Access the application

Backend API: http://localhost:8080

Frontend (if bundled): open in your browser at configured port.

Usage
Admin: Add courses, manage instructors, view analytics.

Instructor: Create quizzes, evaluate results, manage student progress.

Student: Enroll in courses, take quizzes, download certificates.
