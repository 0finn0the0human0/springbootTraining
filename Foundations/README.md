# Phase 1: Core Spring Boot Foundations – Project 1 SRS

Author: Benjamin Soto-Roberts

Date Created: 12/30/2025

Last Updated: 01/05/2026

Version: 1.1

## Summary/Overview

Purpose: Build proficiency with Spring Boot by creating a simple CRUD application with REST endpoints and database integration. No security, privacy, or compliance requirements in Phase 1.

Outcome: A working app exposing REST APIs and a basic Thymeleaf UI for querying products based on the product name containing the search term, backed by an H2 database. 

## Scope

**In Scope:**
-	Spring Boot REST API for Products (CRUD).
-	Spring MVC (Thymeleaf) page for editing product descriptions.
-	Persistence using Spring Data JPA with H2.
-	Basic DTOs and mapping.
-	Basic validation for inputs (e.g., name not blank, price non-negative).

**Out of scope (deferred to Phase 2+):**
- Authentication/authorization, input sanitization beyond basic validation, audit logging, privacy/compliance controls, deployment hardening.

## Objectives/Goals
-	Learn controllers, endpoints, and JSON responses.
-	Practice calling APIs with RestTemplate or WebClient.
-	Understand templating (Thymeleaf) and MVC basics.
-	Learn persistence with Spring Data JPA.
-	Essentials for secure input handling(to be expanded in Phase 2).

## Stakeholders
-	Developer: Solo developer (author, primary user and maintainer)
-	Reviewers: Any collaborators reviewing code on GitHub
-	End users: Demo users interacting with the REST API and the Thymeleaf view

## Functional Requirements
**FR-1 Product entity**
- Create, read, update, delete Product records.
- Fields (minimum): id (Long, auto-generated), name (String), description (String), price (BigDecimal, scale 2).


**FR-2 Product Repository**
-	Create a ProductRepository interface to handle CRUD operations on the Product Entity
    -	Add abstract method to substring search the product name
        - Returns a list of products


**FR-3 DTOs and Validation**
- Request DTO for searching for text in product name:
    - name: not blank, max length (e.g., 100), sanitization regex pattern
- Response DTO for returning required fields.


**FR-4 ProductService Logic**
-	Contains the business logic to return results from the client requests
-	Handles DTO mapping for requests and responses


**FR-5 REST API**
-	Create Product: POST /api/products
-	Get Product by id: GET /api/products/{id}
-	List Products: GET /api/products
-	Update Product: PUT /api/products/{id}
-	Delete Product: DELETE /api/products/{id}
-	Return JSON responses with appropriate HTTP status codes.


**FR-6 Web (Thymeleaf) UI**
- GET / -> form.html
- Show basic validation errors on the form.
- Search contents: : POST / returns results if valid

  
**FR-7 Data Initialization**
-	Seed sample Products on startup via CommandLineRunner for demo purposes.


## Technical Requirements
**TR-1 Platform and Frameworks**
-	Apache Maven 3.9.12
-	Java 21+ (or current LTS)
-	Spring Boot 4.0.1
-	Spring Web, Spring Data JPA, Thymeleaf, Validation (jakarta.validation)
-	H2 in-memory or file-based database for development/demo



