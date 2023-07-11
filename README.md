# Seeker_Pool_Fong_Framework
## 專案簡介：
簡易求職網站，求職者可以找職缺，企業端可以找尋人才。

## 專案功能說明：
製作功能包含會員註冊、將驗證碼存在 Redis 中透過信箱寄發（避免等待，用多執行續處理邊跳轉頁面邊寄發郵件）。並使用 HttpFilter 確認是否登入，將登入狀況存在 HttpSession 中，以及修改會員資料、照片等資訊，還有文章&職缺收藏管理、應徵管理(包含文字搜尋、下拉選單搜尋等功能)，最後是企業端發起面試時間預約，透過郵件寄發，不管求職會員是否登入都可以透過點擊郵件中連結確認面試時間。

## 技術使用
#### 專案建立：Spring Boot Project
#### 前端技術：HTML、CSS、JavaScript、JQuery、AJAX
#### 後端技術：MVC 架構 + Spring Framework、Spring MVC、Spring Boot、Spring Data JPA
#### 資料庫使用：MySQL、Redis

## 補充
#### 功能簡報說明：https://pse.is/559wbx
#### 功能畫面演示：https://yt1.piee.pw/5522yj
