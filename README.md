# Workshop of register
* API
* WEB UI

## Start API 
* NodeJS
* MySQL Database

Start mysql database with table
```
$docker compose up -d
$docker compose ps
```

Start API server
```
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

