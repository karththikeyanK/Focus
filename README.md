# Focus  
**Stay focused, stay mindful**

---

## App Overview

Focus is a productivity app designed to help users manage their social media usage by setting access restrictions and requesting approval for use from assigned approvers. It provides real-time notifications, usage tracking, and analytics to encourage mindful digital habits.

---

## Use Cases

### 1. User Login
- Standard authentication to securely access app features.

### 2. Setting Approvers
- Users can assign one or multiple approvers who have control over and visibility into the user’s social media usage.

### 3. App Blocking with Notification Approval
- Users select specific social media apps they want to restrict.
- Restricted apps are hidden from the user's home screen unless permission is granted.
- When a user tries to open a restricted app, an approval notification is sent to the assigned approver(s).
- Upon approval, a timer is set, and when the timer expires, the app closes automatically.

### 4. Usage Analysis for Approvers
- Approvers can review usage patterns and analyze the user’s social media activity to provide insights or recommendations.

### 5. Restricted App Removal
- Users can request removal of an app from the restricted list. Approvers can then review and approve or deny the request.

---

## Core Functionalities

### 1. Authentication
- Secure login and user authentication.

### 2. Push Notifications
- Real-time notifications using Firebase for access requests, approvals, and timer expirations.

### 3. Time Tracking & Automated App Closure
- Time tracking for app usage and automated app closure when the set time expires.

### 4. Usage Analytics
- Visualizations and reports to help approvers analyze social media usage and identify patterns.

---

## Tech Stack

- **Backend**: Spring Boot for REST APIs and business logic.
- **Frontend**: Flutter for a cross-platform mobile app with a smooth user interface.
- **Messaging/Push Notifications**: Apache Kafka for event streaming and Firebase Cloud Messaging for notifications.
- **Deployment**: Google Cloud Console for hosting and database management, with Docker and CI/CD pipelines.
- **Database**: MySQL for securely storing user data, approvals, and time logs.

---

## App Pages

### 1. Login Page
- Secure authentication page to access the app.

### 2. Add Approver
- Interface for users to assign approvers who monitor and control social media app access.

### 3. Manage App List
- Allows users to add or remove social media apps they want to restrict.

### 4. Approval Request Dashboard (for Approver)
- View pending requests, approve access, and set time limits for usage.

### 5. Usage Analysis (for Approver)
- Analytics dashboard displaying usage patterns and time logs to help users manage social media usage effectively.

### 6. Partner Time Logs
- View past usage logs for each restricted app.

---

## License

All rights reserved under **GINGERX** © 2024 Oct.
