# Hibernate. Part 2

## Task: Implement Service and Repository Layers

### 1. Service Layer Implementation

- Implement the service layer classes based on the interfaces provided in the `service` package.

### 2. Repository Layer Implementation

- Create the repository layer for interacting with the database, ensuring proper data access for each service.

### 3. Unit Testing

- Write unit tests for all service and repository classes using the **`todo_test`** test database to validate the
  functionality of the service layer.

# To-Do List Application: Service and Repository Layer Implementation

## Introduction

This project focuses on implementing the **Service Layer** and **Repository Layer** for the `To-Do List` application.
The goal is to provide a robust and modular structure for managing users, to-do lists, tasks, and collaborators.
Comprehensive unit tests validate the functionality using a dedicated `todolist` database.

---

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Implementation Details](#implementation-details)
  - [Service Layer](#service-layer)
  - [Repository Layer](#repository-layer)
- [Unit Testing](#unit-testing)

---

## Features

1. **Service Layer Implementation**:

- Manage users, to-do lists, tasks, and collaborators.
- Business logic encapsulated within service classes.

2. **Repository Layer Implementation**:

- Database interaction using repositories.
- CRUD operations for all entities.

3. **Unit Testing**:

- Validate service and repository functionalities.
- Tests run on a dedicated `todolist` database.

---

## Technologies Used

- **Java**: Core programming language.
- **Spring Framework**: Dependency injection and bean management.
- **Spring Data JPA**: For repository layer implementation.
- **Hibernate Validator**: For entity validation.
- **JUnit, Mockito**: For unit testing.

## Setup and Installation

1. **Clone the Repository**

```bash
   git clone https://github.com/Andrii-Kosteniuk/SoftServe-IT-academy-Hibernate-Part-2.git 
```

2. **Configure Database**

- Set up the todo_test database for testing.
- Update application.properties or application.yml with database credentials.
- Example configuration for testing:

``` priorities
server.port=8083
spring.application.name=todolist
#spring.sql.init.platform=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/todolist
spring.datasource.username=postgres
spring.datasource.password=root
#spring.jpa.database=POSTGRESQL
spring.jpa.show-sql=true
#spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create-drop
```

3. Add Dependencies Add the following dependencies to your pom.xml or build.gradle:

Example for Maven:

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.validation</groupId>
        <artifactId>jakarta.validation-api</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.3</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
        <version>8.0.1.Final</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish</groupId>
        <artifactId>jakarta.el</artifactId>
        <version>4.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>
</dependencies>
```

3. **Build the Project**

``` bash
mvn clean install
```

## Implementation Details

## **Service Layer**

The *Service Layer* encapsulates business logic and interacts with the repository layer for data access. The following
services are implemented:

1. **UserService:**

  - Methods:

```
    User create(User user);

    User readById(long id);

    User update(User user);

    void delete(long id);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    List<User> getAll();
```

2. **ToDoService:**
  - Methods:

  ```
    ToDo create(ToDo todo);
    
    ToDo readById(long id);
    
    ToDo update(ToDo todo);
    
    void delete(long id);
    
    List<ToDo> getByUserId(long userId);
    
    List<ToDo> getAll();
```

3. **TaskService:**

  - Methods:

```
    Task create(Task task);

    Task readById(long id);

    Task update(Task task);

    void delete(long id);

    List<Task> getAll();

    List<Task> getByTodoId(long todoId);
```

4. **StateService**
  - Methods:
```
    State create(State state);

    State readById(long id);

    State update(State state);

    void delete(long id);

    State getByName(String name);

    List<State> getAll();
```

## **Repository Layer**

The *Repository Layer* uses Spring Data JPA to interact with the database. Repositories provide CRUD operations for the
following entities:

- UserRepository
- ToDoRepository
- TaskRepository
- StateRepository

## Unit Testing

- Test Frameworks
  - JUnit and Mockito: For writing unit tests.

Run Tests

- Execute tests using:

```bash
mvn test
