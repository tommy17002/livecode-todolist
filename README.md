# DATABASE SCHEME / DIAGRAM
![img_3.png](img_3.png)

### USER
#### CREATE USER SUCCESS (ADMIN)
```http
  POST /api/todo/users  
```
##### INPUT
```json
{
  "name" : "Admin 1",
  "username" : "admin1",
  "email" : "admin1@email.com",
  "password" : "admin#1234"
}
```
##### OUTPUT
![img.png](img.png)

#### CREATE USER FAILED (USERNAME ALREADY EXIST)
```http
  POST /api/todo/users  
```
##### INPUT
```json
{
  "name" : "Admin 2",
  "username" : "admin1",
  "email" : "admin2@email.com",
  "password" : "admin#1234"
}
```
##### OUTPUT
![img_1.png](img_1.png)

#### CREATE USER FAILED (USERNAME ALREADY EXIST)
```http
  POST /api/todo/users  
```
##### INPUT
```json
{
  "name" : "Admin 2",
  "username" : "admin2",
  "email" : "admin1@email.com",
  "password" : "admin#1234"
}
```
##### OUTPUT
![img_2.png](img_2.png)

### AUTHENTICATION
#### LOGIN
```http
  POST /api/todo/auth/login  
```
##### INPUT
```json
{
  "username" : "admin1",
  "password" : "admin#1234"
}
```
##### OUTPUT
![img_4.png](img_4.png)

#### REGISTER
```http
  POST /api/todo/auth/register  
```
##### INPUT
```json
{
  "name" : "Arzayn",
  "username" : "adenjen",
  "email" : "arzayn@email.com",
  "password" : "arzaynkhalif"
}
```
##### OUTPUT
![img_6.png](img_6.png)
![img_7.png](img_7.png)

### CATEGORY
#### CREATE CATEGORY (AUTHENTICATED)
```http
  POST /api/todo/categories/create 
```
##### INPUT
Authorization (Add Login Token)
Type - Bearer Token
Token = eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjEiLCJpYXQiOjE3MjE1NDk0ODQsImV4cCI6MTcyMTYzNTg4NH0.MlgrJAElNpq4HD3Ea5r3rMzlm0VCl8FFYlh3IX1DnPg
Body
```json
{
  "name" : "Homework"
}
```
##### OUTPUT
![img_8.png](img_8.png)

### TASK
#### CREATE TASK (AUTHENTICATED)
```http
  POST /api/todo/tasks/create 
```
##### INPUT
Authorization (Add Login Token)
Type - Bearer Token
Token = eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZGVuamVuIiwiaWF0IjoxNzIxNTUwOTc3LCJleHAiOjE3MjE2MzczNzd9.tgZtj0yFPxFtuT9wy5EU7RjExeZed2Xo1fhD8PkNY4w
Body
```json
{
  "user_id" : 3,
  "category_id" : 1,
  "to_do" : "Mengerjakan tugas menggambar dari Ibu Eni",
  "due_date" : "2024-07-24 08:00"
}
```
##### OUTPUT
![img_9.png](img_9.png)
![img_10.png](img_10.png)

#### COMPLETED TASK (AUTHENTICATED)
```http
  PUT /api/todo/tasks/completed 
```
##### INPUT
Authorization (Add Login Token)
Type - Bearer Token
Token = eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZGVuamVuIiwiaWF0IjoxNzIxNTUwOTc3LCJleHAiOjE3MjE2MzczNzd9.tgZtj0yFPxFtuT9wy5EU7RjExeZed2Xo1fhD8PkNY4w
Body
```json
{
  "id" : 1,
  "completed_at" : "2024-07-23 19:00"
}
```
##### OUTPUT (completed become true, add notes if initial notes is null)
![img_11.png](img_11.png)
![img_12.png](img_12.png)
