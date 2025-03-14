# Job-Recommendation-System


## Overview ##

The Job Recommendation System is a Java-based application that helps users find job recommendations based on their skills and preferences. The system provides functionalities for users to create profiles, browse job listings, apply for jobs, and manage their applications.

![WhatsApp Image 2025-03-14 at 19 38 49_ac091935](https://github.com/user-attachments/assets/d052164e-4f7b-4263-8bf0-e3884384d89c)
![WhatsApp Image 2025-03-14 at 19 38 50_e3e1c94f](https://github.com/user-attachments/assets/ff60cdb7-1fcb-4109-9d4f-1478fe771f07)
![WhatsApp Image 2025-03-14 at 19 38 50_61763894](https://github.com/user-attachments/assets/b5c4c7d7-c730-4e5f-9109-e6cd0391a740)


## Features ##

User Authentication: Sign up and log in functionality.

Profile Management: Users can create and update their profiles, including skills and experience.

Job Listings: Browse and search for available jobs.

Job Recommendations: Receive job recommendations based on user skills and interests.

Application Management: Track job applications.

Dashboard: View a personalized dashboard with job suggestions and application status.

Secure Database Connectivity: Uses JDBC to connect to a MySQL database for storing user data and job listings.

## Technologies Used ##

Programming Language: Java (Swing & AWT for GUI)

Database: MySQL

Database Connectivity: JDBC

IDE: IntelliJ IDEA / Eclipse / NetBeans (Choose based on preference)


## Database Schema ##

The project uses MySQL as its database. Below are the key tables:

users: Stores user details such as user ID, name, email, password, and skills.

jobs: Stores job listings including job title, description, company details, and required skills.

applications: Keeps track of job applications with job ID, user ID, and status.

recommendations: Stores recommended jobs for each user based on skills.

## Add JDK in IntelliJ IDEA (Short Version) ##

Download & Install JDK file

Get JDK from Oracle JDK or AdoptOpenJDK.

Open IntelliJ IDEA.

Go to File > Project Structure > Project.

Click New... > JDK, then select the JDK installation folder (C:\Program Files\Java\jdk-XX).

Click OK > Apply.

## Installation & Setup ##
 
Clone this repository: git clone <repository_url>

Import the project into your preferred Java IDE.

Ensure you have MySQL installed and running.

Create a database and import the necessary tables using the provided SQL scripts.

Update the database connection details in DBconnection.java and add JDK connection file.

Compile and run the application.

