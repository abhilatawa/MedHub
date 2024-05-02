# <img src="images/medhub_logo.png" alt="Logo" width="100px"> MedHub

MedHub is a one-stop-shop destination for healthcare. Our mission is to make your healthcare experience easy, regardless of your specific needs. Our platform is designed to simplify the healthcare experience, connecting patients with qualified doctors, enabling doctors to meet with patients effectively and on their own schedule, and helping pharmacists deliver quality care.

## Table of Contents
- [Overview](#overview)
- [Code Coverage](#code-coverage)
- [Test Smells](#test-smells)
- [CI/CD for MedHub](#cicd-for-medhub)
- [Build and Deployment](#build-and-deployment-information)
- [ER Diagram](#er-diagram)
- [Dependencies](#dependencies)
- [Use Case Scenarios](#use-case-scenarios)

## Overview

MedHub is designed to simplify the healthcare process for everyone involved - patients, doctors, and pharmacists. Get started today by registering for an account at http://172.17.0.96:5173/ (Note: must be connected to Dalhousie VPN to access the link).

## Code Coverage

![Static Badge](https://img.shields.io/badge/JUnit_Test_Cases-154-blue)
![Static Badge](https://img.shields.io/badge/Integration_Test_Cases-13-blue)
![Static Badge](https://img.shields.io/badge/Method_Coverage-76%25-blue)
![Static Badge](https://img.shields.io/badge/Line_Coverage-83%25-blue)
<br><img src="images/test_coverage.png" alt="Test Coverage" width="500px"><br>

## Code  Smell Statistics

<table>
<thead>
<tr>
<th>Smell Type</th>
<th>Total Smells</th>
<th>True Positive</th>
<th>False Positive</th>
</tr>
</thead>
<tbody>
<tr>
<td>Implementation Smells</td>
<td>130</td>
<td>55</td>
<td>75</td>
</tr>
<tr>
<td>Design Smells</td>
<td>189</td>
<td>14</td>
<td>175</td>
</tr>
<tr>
<td>Architecture Smells</td>
<td>13</td>
<td>2</td>
<td>11</td>
</tr>
<tr>
<td>Test Smells</td>
<td>36</td>
<td>18</td>
<td>18</td>
</tr>
<tr>
<td><b>Total Smells</b></td>
<td><b>368</b></td>
<td><b>89</b></td>
<td><b>279</b></td>
</tr>
</table>

See the MedHub code smells summary CSV for justifications of smells in the case of false positives, and explanations of how we refactored in the case of true positives.

## CI/CD for MedHub

The information below details the steps in the MedHub CI/CD pipeline used for building and deploying the application.

### Build Stage
- Builds the backend using the command `mvn clean package` and creates the jar file in the target directory.

### Test Stage
- Runs all test cases in the project using the command `mvn test`.

### Code Quality Stage
- Uses DesigniteJava to run a report on code smells and saves the resulting artifacts in the code_smells directory with the command `java -jar DesigniteJava.jar -i ASDC -o code_smells`.

### Publish Stage
- Logs into Docker Hub and builds the Docker images for the backend and frontend code, tags them with a unique identifier and pushes these to Docker Hub.

### Deployment Stage
- Deploys the backend and frontend Docker containers to the VM URL.

## Build and Deployment Information

MedHub is a Maven-based project using the Spring framework for the backend and React for frontend.

To deploy this project on your local machine, follow the steps below

### Backend

 - Download the Java Development Kit (JDK) from the Oracle JDK website: https://www.oracle.com/ca-en/java/technologies/downloads/. Select the most recent version for your operating system and follow the installation instructions.
 - Download the latest version of Apache Maven from the Apache Maven website: https://maven.apache.org/download.cgi. Follow installation instructions.
 - Modify the application.properties file found in the Backend folder to configure the project to your local database.
 - Navigate to the project directory in your terminal and run `mvn clean install` to build the code.
 - Once the build is successful, navigate to the target directory of the project directory and run `java -jar ASDC.jar`.

### Frontend

- Install Node.js from https://nodejs.org/en/download. Select the most recent version for your operating system. Follow the installation instructions to install Node.js and npm (Node Package Manager).
- Open your terminal and navigate to the project directory.
- Install project dependencies by running `npm install`.
- Start the development server by running `npm start`.
- Navigate to http://localhost:3000/ in your browser to view the application.

## Dependencies
The following is a list of dependencies and libraries used in this application, along with their purpose in the application:

| No | Library                             | Version | Description                                                                                      |
|----|-------------------------------------|---------|--------------------------------------------------------------------------------------------------|
| 1  | Spring Boot Starter Data JPA        | 3.2.2   | Used for data access and management with the database.                                          |
| 2  | Spring Boot Starter Web             | 3.2.2   | Enables setting up the application with web functionalities such as RESTful services and web controllers. |
| 3  | Spring Boot Starter Validation      | 3.2.2   | Used for defining validation rules within the application.                                       |
| 4  | Spring Boot Starter Mail            | 3.2.2   | Provides capabilities for sending emails, used for user registration and password reset in MedHub. |
| 5  | Spring Boot Devtools                | 3.2.2   | Includes general tools that aid developers during the development phase of the application.       |
| 6  | JJWT API                            | 0.12.5  | Used for implementing authentication and authorization mechanisms in the application.            |
| 7  | JJWT Impl                           | 0.12.5  | Used for performing JWT authentication operations.                                               |
| 8  | MySQL Connector J                   | 8.3.0   | Allows the application to connect to the MySQL database.                                         |
| 9  | Spring Boot Configuration Processor | 3.2.2   | Helps in configuring the application during compilation.                                          |
| 10 | Lombok                              | 1.18.32 | Automatically generates boilerplate code such as getters and setters, reducing manual coding efforts. |
| 11 | Spring Boot Starter Security        | 3.2.2   | Provides features for authorization and authentication within the application.                   |
| 12 | Commons Codec                       | 1.16.0  | Includes encoding algorithms useful for tasks like profile picture uploads.                       |
| 13 | Jakarta Validation API              | 3.0.2   | Utilized for built-in constraint validation in the application.                                   |
| 14 | JUnit Vintage Engine                | 5.10.2  | Enables running tests using JUnit and ensures compatibility with older versions of JUnit.         |

## ER Diagram
The MedHub project uses a relational database to store and access data. The ER Diagram below details the key relationships and attributes found in the MedHub entities.
<br><img src="images/Medhub_ERD.png" alt="MedHub ERD" width="750px"><br>

## Use case scenarios

### Homepage
- Upon accessing the MedHub website, users can see key information about what MedHub offers, with options to register and sign in to the site.
<br><img src="images/medhub_homepage.png" alt="MedHub Homepage" width="500px"><br>

### Doctor and Pharmacy Registration
- Doctors and pharmacists can sign up by providing their name, email address, home or practice address, medical license number and creating a password. 
- Upon registration, doctor and pharmacists will not be able to access their MedHub account until the admin verifies them.
<br><img src="images/doctor_registration.png" alt="Doctor Registration" width="300"><br>

### Patient Sign Up
- Patients can sign up for a MedHub account by providing their name, email address, and creating a password.
<br><img src="images/patient_signup.png" alt="Patient Signup" width="300"><br>

### Authentication
- All users can sign in to their account using the SIGN IN button on the homepage and entering their email address and password.
  <br><img src="images/signin.png" alt="User Signin" width="300"><br>

### Patient Dashboard
Upon logging in, patients will be brought to their dashboard which will display all the key functionalities they can access.
<br><img src="images/patient_dashboard.png" alt="Patient Dashboard" width="500px"><br>
1. **Profile:** Patients can view and modify their personal details
<br><img src="images/patient_profile.png" alt="Patient Profile" width="500px"><br>
2. **Doctor Catalog:** Patients can view all the doctors in the system and filter based on specialty when they are looking for a particular doctor. The doctor catalog page displays the option to book an appointment with the doctor
<br><img src="images/doctor_catalog.png" alt="Doctor Catalog" width="500px"><br>
3. **Chat:** Patients can chat in real-time with the doctor to clarify questions or ask about upcoming and past appointments, as well as send multimedia documents back and forth.
<br><img src="images/patient_chat.png" alt="Patient Chat" width="500px">
<br><img src="images/patient_multimedia.png" alt="Patient Blogs" width="500px"><br>
4. **Appointments:** After selecting "book an appointment" for a doctor found in the doctor catalog, patients can select a date and time for their appointment based on the doctor's real availability. They can also leave ratings and feedback for completed appointments.
<br><img src="images/patient_appointments2.png" alt="Patient Appointment" width="500px">
<br><img src="images/patient_feedback.png" alt="Patient Feedback" width="500px"><br>
5. **Blogs:** Patients can view articles written by doctors about health tips, trends, or essential health information. The patient can filter by doctor to see the blogs that are most relevant to them.
<br><img src="images/patient_blogs.png" alt="Patient Blogs" width="500px"><br>

### Doctor Dashboard
Upon logging in, doctors will be brought to their dashboard which will display all the key functionalities they can access.
<br><img src="images/doctor_dashboard.png" alt="Doctor Dashboard" width="500px"><br>
1. **Personal Details:** Doctors can view and modify their personal details
<br><img src="images/doctor_profile.png" alt="Doctor Profile" width="500px"><br>
2. **Availability:** Doctors can update their availability for appointment bookings.
<br><img src="images/doctor_availability.png" alt="Doctor Availability" width="500px"><br>
3. **Appointment:** Doctors can view all their upcoming appointments and add prescriptions for appointments that have been completed
<br><img src="images/doctor_appointments.png" alt="Doctor Appointments" width="500px">
<br><img src="images/adding_prescriptions.png" alt="Adding prescriptions" width="500px"><br>
4. **Consultation:** Doctors can view all their past consultations
<br><img src="images/doctor_consultation.png" alt="Doctor Consultation" width="500px"><br>
6. **Pharmacy:** Doctors can view the prescriptions they have given to their patients
<br><img src="images/doctor_pharmacy.png" alt="Doctor Pharmacy" width="500px"><br>
7. **Blog:** Doctors can see all the blogs they have written and write new blogs posts
<br><img src="images/doctor_blogs.png" alt="Doctor Blogs" width="500px"><br>
8. **Chat:** Doctors can chat with their patients to clarify questions or discuss upcoming and past appointments, as well as send multimedia documents back and forth.
<br><img src="images/doctor_chat.png" alt="Doctor Chat" width="500px"><br>
9. **Feedback:** Doctors can see their overall feedback rating and view comments left by their patients
<br><img src="images/doctor_feedback.png" alt="Doctor Availability" width="500px"><br>
10. **Notification preferences:** Doctors can update their preferences to receive notifications
<br><img src="images/notification_preferences.png" alt="Doctor Preferences" width="500px"><br>
11. **Change password:** Doctors can change the password for their account
    <br><img src="images/doctor_change_password.png" alt="Doctor Change Password" width="500px"><br>

### Pharmacy Dashboard
Upon logging in, pharmacists will be brought to their dashboard which will display all the key functionalities they can access.
1. **Personal Details:** Pharmacist can view and modify their personal details
<br><img src="images/pharmacy_personal_details.png" alt="Pharmacist Personal Details" width="500px"><br>
2. **Prescriptions:** Pharmacists can view all prescription orders that have been placed by the doctor
<br><img src="images/pharmacist_prescriptions.png" alt="Pharmacist Prescriptions" width="500px"><br>
3. **Change password:** Pharmacists can change the password for their account
<br><img src="images/doctor_change_password.png" alt="Doctor Change Password" width="500px"><br>

### Admin Dashboard
Admins control who can use MedHub, in order to ensure only verified medical professionals can represent themselves. 
On their dashboard, Admins will see the option to accept or reject pending Doctor and Pharmacist approvals.
<br><img src="images/admin_dashboard.png" alt="Admin Dashboard" width="500px"><br>
You will receive an email informing you of the decision of the admin after validating your medical license.