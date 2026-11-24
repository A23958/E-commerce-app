# E-Commerce Web App

A full-stack portfolio project with a React storefront and a Spring Boot REST API backed by PostgreSQL.

## Features

- Account registration and sign-in with Spring Security, BCrypt and CSRF-protected sessions
- Product browsing, search, category filters and product images
- Product creation, editing and deletion
- A shopping cart with prices in South African rand
- Demo checkout that validates and deducts inventory in a single PostgreSQL transaction

Checkout simulates a purchase by updating stock. Payment processing and order history are outside the scope of this demo. Run it locally with test accounts. Product management currently requires sign-in. Add role-based access before hosting a shared demo with open registration.

## Stack

React, Vite, Axios, Bootstrap, Java 17, Spring Boot, Spring MVC, Spring Security, Spring Data JPA, JDBC, PostgreSQL, Lombok and Maven. Postman was used to test the API.

## Run locally

Install a JDK compatible with Java 17, Node.js with npm, and PostgreSQL. Create an empty PostgreSQL database named `ecommerce` using pgAdmin or `CREATE DATABASE ecommerce;` from an existing database connection. Use a local database account that can create tables in the new database.

Open a terminal in `backend` and set the password for **your local database account**. Spring Boot defaults to `localhost:5432`, database `ecommerce`, and user `postgres`. Set `DB_URL` and `DB_USERNAME` if your configuration differs.

PowerShell

```powershell
$env:DB_PASSWORD = Read-Host "Local PostgreSQL password"
.\mvnw.cmd spring-boot:run
```

Bash

```bash
read -rsp "Local PostgreSQL password: " DB_PASSWORD
export DB_PASSWORD
bash ./mvnw spring-boot:run
```

Spring Boot runs on port 8080 and Hibernate creates the tables in the empty database. In a second terminal, open `frontend` and run:

```sh
npm ci
npm run dev
```

Open the URL printed by Vite. Select **Create an account**, register a test user, and sign in. Add a sample product with an image, price, positive stock and availability enabled. Add it to the cart and confirm checkout.
