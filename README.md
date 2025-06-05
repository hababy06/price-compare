# Price Compare Project

## 專案介紹

這是一個基於 Spring Boot（後端）與 React（前端）的比價網站，支援條碼掃描、商品查詢、價格比對、圖片上傳、會員系統等功能。適合練習全端開發與現代企業級架構[1]。

---

## 專案結構

```
your-project/
├── src/
│   ├── main/java/com/example/demo/
│   └── test/java/com/example/demo/
├── frontend/
├── db/migration/
├── pom.xml
└── README.md
```
---

## 技術選型

- **後端**：Spring Boot、JPA、Spring Security、Lombok
- **前端**：React、axios、zxing-js/react-qr-barcode-scanner
- **資料庫**：MySQL 或 PostgreSQL
- **部署**：Docker、Kubernetes（可選）

---

## 啟動方式

1. 設定資料庫連線於 `src/main/resources/application.properties`
2. 以 IDE 或執行 `./mvnw spring-boot:run` 啟動後端
3. 進入 `frontend/` 執行 `npm install && npm start` 啟動前端

---

## API 範例

- `GET /api/products`：查詢所有商品
- `POST /api/products`：新增商品
- `PUT /api/products/{id}`：更新商品
- `DELETE /api/products/{id}`：刪除商品

---

## 資料表欄位說明

| 欄位         | 說明         |
| ------------ | ------------ |
| id           | 主鍵         |
| barcode      | 條碼         |
| name         | 商品名稱     |
| description  | 商品描述     |
| imageUrl     | 圖片網址     |
| createdAt    | 建立時間     |
| updatedAt    | 更新時間     |

---

## 功能說明

- **商品查詢**：輸入條碼或商品名稱，顯示比價資訊
- **價格回報**：上傳價格與圖片，標註優惠期限
- **會員系統**：登入/登出、管理者審核
- **資料管理**：商品、商店、價格資料維護

---

## 測試

- 可用 Postman 或 curl 測試 API
- 前端可直接於本地瀏覽器操作

---

## 授權

MIT
