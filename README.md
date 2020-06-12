# Template API

This is a template Spring Boot API which includes:

- Spring Security login set up (credentials are checked against a MYSQL database)
- User model, repository and DB migration to enable login and account set up
- JWT set up so a JWT is provided on login and any requests to endpoints are filtered to check for a valid JWT
- Swagger set up so all endpoints can be viewed and tested (includes header setup so a JWT can be provided)
