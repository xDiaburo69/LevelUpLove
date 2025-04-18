[![Spring Boot](https://img.shields.io/badge/Spring_Boot-v3.3.10-green?logo=spring)](https://github.com/xDiaburo69/LevelUpLove/tree/main/Backend)
[![Angular](https://img.shields.io/badge/Angular-v19-red?logo=angular)](https://github.com/xDiaburo69/LevelUpLove/tree/main/Frontend)
[![Coverage](https://img.shields.io/badge/Coverage-20%25-lightgrey)](#)
[![License: MIT](https://img.shields.io/badge/license-MIT-yellow.svg)](./LICENSE)


# LevelUpLove – Dating App

LevelUpLove is a vocational‑training team project: a simple yet powerful dating app where users register, create profiles, find matches and chat in real time.

---

## 🛠️ Tech Stack

- **Backend:** Java 17, Spring Boot v3.3.10, PostgreSQL, JWT, WebSocket (STOMP/SockJS)  
- **Frontend:** Angular v19, Angular Material
- **Testing & DevOps:** JUnit 5, Mockito, MockMvc, Swagger/OpenAPI 

---

## 🚀 Quick Start

```bash
git clone https://github.com/xDiaburo69/LevelUpLove.git
cd LevelUpLove/backend
./mvnw spring-boot:run
cd ../frontend
npm install
ng serve --open
```

---

## ✨ Features & Highlights

- 🔒 **Secure Auth:** Registration & login with JWT + BCrypt  
- 🖼️ **Profile Management:** Create/edit bio & interests, upload and display profile images  
- ❤️ **Matching:** Interest‑based matching algorithm with percentage score & suggestions endpoint  
- 💳 **Swipe UI:** Card‑based swipe interface (like Tinder) with smooth animations  
- 🎨 **Responsive Design:** Mobile‑first layouts built with Angular Material & Flexbox/Grid  
- ⚙️ **State Management:** Centralized data flow using NgRx (or RxJS‑powered services)  
- 💬 **Real‑Time Chat:** REST messaging + WebSocket chat widget available across all pages  
- 🧪 **Form Validation & Feedback:** Client‑side validation, error messages and success toasts  
- 📊 **API Docs & Sandbox:** Interactive Swagger/OpenAPI UI for all REST endpoints  
- 🧪 **Tests & Quality:** Unit tests (JUnit 5/Mockito, Jasmine/Karma), integration tests (MockMvc)  

---

## 🖼️ Screenshots & GIFs

Will be added later

<div align="center">
<!--   <img src="docs/screenshots/landing.png" alt="Landing Page" width="30%"/>
  <img src="docs/screenshots/swipe.gif" alt="Swipe Interaction" width="30%"/>
  <img src="docs/screenshots/chat.png" alt="Chat Window" width="30%"/> -->
</div>

---

## 📐 Architecture Diagram

         +------------------------------+
         |    Angular Frontend (v19)    |
         |  • Angular Material          |
         +-------------+----------------+
                       |
           HTTPS/REST  |  WebSocket (STOMP/SockJS)
                       |
         +-------------v----------------+
         |    Spring Boot Backend       |
         |  • Java 17, Spring Boot v3.3 |  
         |  • JWT‑Auth & Security       |
         +------+-----------+-----------+
                |           |
          JDBC  |           |  File I/O (Profil‑Bilder)
                |           |
         +------v---+  +----v----------+
         |PostgreSQL|  | File Storage  |
         |  DB      |  | (/uploads/…)  |
         +----------+  +---------------+


---
## 🚧 Roadmap & Future Improvements

| Feature                         | Status      |
|---------------------------------|-------------|
| Real‑Time Messaging             | In planning |
| Matching‑Score Tuning           | Backlog     |
| Push‑Notifications              | Backlog     |
| Social‑Login (OAuth2)           | Backlog     |
| Profile‑Completion Reminders    | Backlog     |

---

## 👥 About Us

**Diaburo (Backend)**  
- **Infrastructure & Setup:** Spring Boot (Java 17/Maven), PostgreSQL, health‑check endpoint  
- **Authentication & User Management:** User entity with roles, registration/login using JWT, BCrypt password hashing  
- **Profile Management:** Profile entity (bio, interests), profile‑image upload and access via REST  
- **Matching & Likes:** Like‑ and Match‑entities, interest‑based matching algorithm, suggestions endpoint  
- **Messaging & Real‑Time Chat:** REST endpoints for messages plus WebSocket (STOMP over SockJS) integration  
- **Error Handling & Logging:** Global exception handling, uniform error responses, SLF4J/Logback logging, rate limiting  
- **Testing & Documentation:** Unit tests (JUnit 5/Mockito), integration tests (MockMvc), API docs with Swagger/OpenAPI

**Madame Parker (Frontend)**  
- **Project Setup & Design:** Angular project with Angular Material, responsive layouts, wireframes/mockups for landing, swipe, profile, chat, contact/about  
- **Routing & Component Architecture:** Angular Router, header/footer, persistent chat component  
- **Swipe Mechanics & Profile Display:** Card‑based swipe UI, seamless API integration for profile data  
- **Profile Pages & Form Validation:** Edit‑and‑view profile pages, image upload, validation feedback  
- **Chat Interface & Real‑Time Window:** Conversation view, WebSocket integration with JWT, chat window available across all pages  
- **Contact & About Pages:** Contact form with validation, team showcase, project vision  
- **Guards, Services & State Management:** Auth guards, centralized API services

---

## 📄 License

This project is licensed under the MIT License – see the [LICENSE](./LICENSE) file for details.

---

## 📬 Contact & Socials

- **GitHub:** <br>
[Madame Parker](https://github.com/Madame-parker) <br>
[Diaburo](https://github.com/xDiaburo69)

- **LinkedIn:** <br>
[Diaburo](https://www.linkedin.com/in/jordan-betz-927b77310)  
