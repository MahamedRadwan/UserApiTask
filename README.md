# UserApiTask
## 📌 Overview
This project is a **REST API Test Automation Framework** built using **Java**, **Rest Assured**, and **TestNG**. It automates API testing for [ReqRes](https://reqres.in/) and includes:

✅ **Dynamic Data & Assertions** (Using Java Faker)  
✅ **Data-Driven Testing** (Using JSON Data Provider)  
✅ **Advanced Logging** (Using Log4j)  
✅ **Allure Reporting** 

## 📦 Project Structure
```plaintext
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── api       # API Endpoints
│   │   │   ├── config    # Configurations
│   │   │   ├── models    # POJO Classes
│   │   │   ├── utils     # Utilities (RestUtils, Logging)
│   │   ├── resources    # log4j2.xml, test data
│   ├── test
│   │   ├── java
│   │   │   ├── tests     # Test Cases (TestNG)
│   ├── allure-results   # Allure Report Output
├── testng.xml           # TestNG Configuration
├── pom.xml              # Maven Dependencies
└── README.md            # Documentation
```

## 🔧 Setup Instructions
### 1️⃣ Clone the Repository

### 2️⃣ Configure `config.properties`
Modify `src/main/resources/config.properties` for base URL and other settings.

```properties
baseUrl=https://reqres.in
```

### 3️⃣ Run API Tests
```sh
mvn test
```

## 🚀 Features & Enhancements
### ✅ **Dynamic Data & Assertions**
- Uses **Java Faker** to generate random usernames & job titles.
- Validates **response time, headers, and multiple assertions**.

### ✅ **Negative & Edge Case Testing**
- Tests for **missing fields, null values, invalid users, rate limiting**.
- Validates **error codes (400, 404, 500)**.

### ✅ **Data-Driven Testing (JSON)**
- Test cases use **external JSON files** for parameterized tests.
- Example JSON file (`test-data.json`):
  ```json
  {
    "users": [
      { "name": "Alice", "job": "QA Engineer" },
      { "name": "Bob", "job": "Software Developer" }
    ]
  }
  ```

### ✅ **Advanced Logging (Log4j2)**
- Logs **API requests, responses, and errors**.
- Configured in `log4j2.xml`.
- Logs saved in `logs/automation.log`.

### ✅ **Allure Reporting **
- Generates **detailed HTML reports**.
- To view the report:
  ```sh
  allure serve allure-result
  ```

### ✅ **Test Execution via TestNG**
- Runs all test classes via `testng.xml`.
- Includes **Allure TestNG Listener** for better reporting.

