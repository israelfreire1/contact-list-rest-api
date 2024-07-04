# Contact list API 

---

![Kotlin](https://img.shields.io/badge/Kotlin-993399?&style=for-the-badge&logo=kotlin&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![H2](https://img.shields.io/badge/H2-111793?style=for-the-badge&logo=H2&logoColor=yellow)
![License-MIT](https://img.shields.io/badge/license-MIT-red)


This project is an API built using Kotlin, Spring Boot, Flyway Migrations,
H2 as the database, and Swagger for documentation.

The API simulates the functionality of a contact list. Users can create a
contact into the list, get the all contacts stored, updating a contact  and delete
the contact.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database](#database)



### Installation

---

1.Clone the repository:

```bash
$ git clone https://github.com/israelfreire1/contact-list-rest-api.git
```

2. Install dependencies with Gradle


### Usage

---

1. Start application with Gradle
2. The API will be accessible by directly manipulating the methods GET,
POST, PUT and DELETE at http://localhost:8080
3. The documetantion with Swagger will be accessible at http://localhost:8080/swagger-ui/index.html
4. Verify the data stored in the H2 Database at http://localhost:8080/h2-console

### API Endpoints

```markdown
GET/contacts - Retrieve a list of all contacts.
```
```markdown
GET/contacts/{id}  - Retrieve a specific contact by ID.
```
```markdown
POST/contacts/ -  Create a new contact.
```
```markdown
PUT/contacts/{id} - Updating a existing contact.
```
```markdown
DEL/contacts/{id} - Delete a specific contact by ID.
```


### Database

---

The project utilizes [H2](https://www.h2database.com/html/main.html) as the database. The necessary database migrations
are  managed using [Flyway](https://flywaydb.org/).
