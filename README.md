
# SpringBoot Roadmap 

*At the end of each phase, I will build a project to showcase what I’ve learned. Each build comes from a Spring Guide (https://spring.io/guides), 
lives in its own folder with pinned dependencies, and includes a learned.txt capturing key takeaways, gaps I discovered, and topics I researched further to complete the project.*

## **Phase 1: Core Spring Boot Foundations**
- Building a RESTful Web Service – Learn controllers, endpoints, and JSON 
responses.
- Consuming a RESTful Web Service – Practice calling APIs with RestTemplate or 
WebClient.
- Serving Web Content with Spring MVC – Understand templating (Thymeleaf) and 
MVC basics.
- Accessing Data with JPA – Learn persistence with Spring Data JPA.
- Validating Form Input – Essential for secure input handling.
  
*Goal: Be comfortable building CRUD apps with REST endpoints and database integration.*

## **Phase 2: Security Fundamentals**
- Authenticating a User with LDAP – Teaches external directory integration.
- Securing a Web Application – Core Spring Security concepts: filters, 
authentication, authorization.
- Enabling Cross-Origin Requests (CORS) – Important for APIs consumed by 
browsers.
- Accessing Vault / Vault Configuration – Learn secret management with HashiCorp 
Vault.

*Goal: Understand how Spring Security plugs into apps, and how to protect endpoints.*

## **Phase 3: Modern Authentication & OAuth2**
- Spring Boot and OAuth2 – Social login and SSO with GitHub/Facebook.
- Spring Security and Angular – Secure SPA architecture with JWT/OIDC.
- Building a Gateway – Learn API gateway patterns with authentication.

*Goal: Move from legacy LDAP to modern OAuth2/OIDC flows, which dominate secure apps 
today.*

## **Phase 4: Advanced Topics**
- Messaging with RabbitMQ/Redis/Kafka – Secure message-driven architectures.
- Building a Reactive RESTful Web Service – Learn WebFlux and reactive 
programming.
- Deploying to Kubernetes/Azure – Cloud-native deployment with secure configs.
- Observability with Spring – Monitoring and metrics for secure operations.

*Goal: Be production-ready: secure, observable, and cloud-deployable.*
