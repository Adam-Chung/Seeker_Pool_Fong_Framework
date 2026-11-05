# Seeker's Pool — Spring Boot 個人作品 / Personal Project

Seeker's Pool 是一個求職網站的個人開發作品，涵蓋會員管理、職缺與文章收藏、求職應徵、信箱驗證與面試邀請。本版本以 **Spring Boot、Spring MVC 與 Spring Data JPA** 實作後端，透過 Controller、Service 與資料存取層分工，練習框架化的 Web 應用開發。

Seeker's Pool is a personal job-platform project covering member management, saved jobs and articles, applications, email verification, and interview invitations. This version uses **Spring Boot, Spring MVC, and Spring Data JPA**, with responsibilities organized across controllers, services, and persistence components to practice framework-based web development.

## 核心功能 / Key Features

- **會員管理**：會員註冊、登入、資料修改與圖片上傳。<br>**Member management**: Registration, login, profile updates, and image uploads.
- **Session 管理**：以 HttpSession 保存登入狀態，搭配 Filter 檢查。<br>**Session management**: HttpSession-based login state with filter checks.
- **信箱驗證**：以郵件進行驗證，Redis 暫存驗證碼。<br>**Email verification**: Email-based verification with temporary Redis codes.
- **求職與收藏**：管理應徵紀錄、職缺收藏及文章收藏。<br>**Applications and favorites**: Manage applications, saved jobs, and saved articles.
- **面試邀請**：候選面試時段與郵件確認流程。<br>**Interview invitations**: Proposed interview slots and email confirmation.

## 後端架構 / Backend Architecture

`Controller → Service → Repository / DAO → Database`

Spring MVC Controller 接收 Web 請求，Service 協調應用流程，資料存取層整合 Spring Data JPA，並透過 Redis 與郵件支援相關功能。這是學習實作，**不代表企業端登入與權限管理已完成正式產品要求**。

Spring MVC controllers handle requests, services coordinate workflows, and the persistence layer uses Spring Data JPA. Redis and email support relevant features. This is a learning implementation and **does not imply production-grade employer authentication and authorization**.

## 技術棧 / Technology Stack

- Java 11、Spring Boot 2.7.12、Spring MVC、Spring Data JPA
- Servlet Filter、HttpSession、MySQL、Spring Data Redis
- HTML、CSS、JavaScript、jQuery、AJAX、JavaMail
- Maven、WAR

## 本機執行 / Running Locally

請準備 Java 11、Maven、MySQL、Redis 與測試郵件環境。根據自己的環境調整 `src/main/resources/application.yml` 中的資料庫與 Redis 設定，替換範例帳密，另行準備資料庫 schema 與測試資料，再以 Spring Boot 啟動或依 WAR 設定部署。本專案不保證在全新環境中直接啟動。

Prepare Java 11, Maven, MySQL, Redis, and a test email environment. Adjust the database and Redis settings in `src/main/resources/application.yml`, replace demonstration credentials, prepare the database schema and test data, then start with Spring Boot or deploy the WAR. Startup in a fresh environment is not guaranteed.

## 已知限制與安全提醒 / Limitations and Security Notes

本專案是個人學習與展示作品，**不是可直接上線的正式產品**。部分企業端流程使用示範資料；登入、Session、檔案上傳、寄信與設定檔仍需安全強化。請勿沿用 Repository 內的開發用帳密或直接公開部署。

This is a personal learning and portfolio project, **not a production-ready product**. Some employer-side workflows use demonstration data; authentication, sessions, file uploads, email handling, and configuration need further security hardening. Do not reuse repository development credentials or deploy the service directly to the public internet.

## 相關作品 / Related Project

Servlet／JDBC 版本 / Servlet and JDBC version: [Seeker_Pool_Fong_Base](https://github.com/Adam-Chung/Seeker_Pool_Fong_Base).


## 補充
#### 功能簡報說明：https://pse.is/559wbx
#### Feature Presentation: https://pse.is/559wbx

#### 功能畫面演示：https://yt1.piee.pw/5522yj
#### Feature Demo Video: https://yt1.piee.pw/5522yj
