# Workshop of register
* API
* WEB UI

## 1. Start API 
* NodeJS
* MySQL Database

Start mysql database with table
```
$docker compose up -d
$docker compose ps
```

Start API server
```
$cd api
$npm start
```

Testing API
```
POST /api/register
Content-Type: application/json

{
  "name": "John",
  "age": 65,
  "phone": "0123456789",
  "email": "testemail.com",
  "password": "12345",
  "address": ""
}
```

## 2. Start Web
```
$cd web

$npm install
$npm run dev
``` 

Access in web browser
* http://localhost:5173/

## 3. Start all with docker
* database
* api
* web

```
$docker compose build
$docker compose up -d
```

Access in web browser
* http://localhost:8000/

