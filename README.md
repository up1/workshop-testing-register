# Workshop of register
* API
* WEB UI
* Mobile apps
  * Android
  * iOS

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

## 4. Mobile testing with [Appium](https://github.com/appium/appium)
* NodeJS


### Install appium server
```
$npm install -g appium
$appium
```

### Install appium driver
```
$appium driver list
$appium driver list --installed

$appium driver install <driver-name>
```

### Install [appium doctor](https://www.npmjs.com/package/@appium/doctor)
```
$npm install @appium/doctor -g
$appium-doctor --android
$appium-doctor --ios
```

### Install [appium inspector](https://github.com/appium/appium-inspector)
* GUI Mode

Config with Android
```
{
  "platformName": "Android",
  "appium:automationName": "UiAutomator2",
  "appium:appPackage": "com.demo.registerapp",
  "appium:deviceName": "R58M36QKSJK"
}
```

Config with iOS
```
{
  "platformName": "iOS",
  "appium:deviceName": "iPhone 15 Pro",
  "appium:automationName": "XCUITest",
  "appium:bundleId": "dev.RegisterApp",
  "appium:connectionRetryTimeout": 60000,
  "appium:noReset": true
}
```




