# Lost and Found System Backend

Spring Boot backend for the Lost and Found System coursework. Provides JWT-based auth, item reporting, and claim request management.

## Tech stack

- Java 21
- Spring Boot 3.5
- Spring Web, Spring Data JPA, Spring Security
- MySQL
- JWT (jjwt)
- ModelMapper
- Lombok

## How it runs

- App entry: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/LostAndFoundSystemApplication.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/LostAndFoundSystemApplication.java)
- Base context path: `/lnf` (from [src/main/resources/application.properties](src/main/resources/application.properties))
- API base path: `/api/v1`
- CORS allows `http://localhost:3000` (from [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/config/CorsConfig.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/config/CorsConfig.java) and [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/config/SecurityConfig.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/config/SecurityConfig.java))

## Configuration

Edit database and JWT settings in [src/main/resources/application.properties](src/main/resources/application.properties).

- `spring.datasource.*` MySQL connection
- `spring.jpa.*` Hibernate settings
- `jwt.secret` and `jwt.expiration`

## API overview

All routes are under `/lnf/api/v1`.

Auth (public): [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/controller/AuthController.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/controller/AuthController.java)

- `POST /auth/signup` register a user
- `POST /auth/login` login and get JWT

Items: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/controller/ItemController.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/controller/ItemController.java)

- `GET /items` health check
- `POST /items` create item
- `PATCH /items?itemId=...` update item
- `DELETE /items/{itemId}` delete item
- `GET /items/{itemId}` get item by id
- `GET /items/getall` list items

Requests: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/controller/RequestController.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/controller/RequestController.java)

- `GET /requests` health check
- `POST /requests` create claim request
- `PATCH /requests?requestId=...` update request
- `DELETE /requests/{requestId}` delete request
- `GET /requests/{requestId}` get request by id
- `GET /requests/getall` list requests
- `GET /requests/pending` list pending requests

Users (ADMIN/STAFF): [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/controller/UserController.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/controller/UserController.java)

- `GET /users` health check
- `POST /users` create user
- `PATCH /users?userId=...` update user
- `DELETE /users/{userId}` delete user
- `GET /users/{userId}` get user by id
- `GET /users/getall` list users

## Security

- JWT filter: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/security/JwtAuthenticationFilter.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/security/JwtAuthenticationFilter.java)
- JWT utility: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/security/JwtUtil.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/security/JwtUtil.java)
- User details: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/security/CustomUserDetails.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/security/CustomUserDetails.java)
- UserDetailsService: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/impl/CustomUserDetailsService.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/impl/CustomUserDetailsService.java)
- Security rules: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/config/SecurityConfig.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/config/SecurityConfig.java)

Role rules (see [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/enums/Role.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/enums/Role.java)):

- Public: `/auth/**`, `/items/**`
- Authenticated: `POST/GET /requests`
- ADMIN/STAFF: `/users/**`, `PUT/PATCH/DELETE /requests/**`

## Data model

Entities:

- User: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/entities/UserEntity.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/entities/UserEntity.java)
- Item: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/entities/ItemEntity.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/entities/ItemEntity.java)
- Request: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/entities/RequestEntity.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/entities/RequestEntity.java)

Enums:

- Item status: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/enums/ItemStatus.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/enums/ItemStatus.java)
- Request status: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/enums/RequestStatus.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/enums/RequestStatus.java)
- Role: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/enums/Role.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/enums/Role.java)

DTOs:

- Auth request/response: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/AuthRequest.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/AuthRequest.java), [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/AuthResponse.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/AuthResponse.java)
- User: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/UserDto.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/UserDto.java)
- Item: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/ItemDto.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/ItemDto.java)
- Request: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/RequestDto.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dto/RequestDto.java)

Mapping helpers:

- ModelMapper config and conversions: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/util/EntityDtoConversion.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/util/EntityDtoConversion.java)
- Id/date helpers: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/util/UtilityData.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/util/UtilityData.java)

Repositories:

- Users: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dao/UserDao.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dao/UserDao.java)
- Items: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dao/ItemDao.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dao/ItemDao.java)
- Requests: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dao/RequestDao.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/dao/RequestDao.java)

Services:

- Interfaces: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/UserService.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/UserService.java), [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/ItemService.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/ItemService.java), [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/RequestService.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/RequestService.java)
- Implementations: [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/impl/UserServiceImpl.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/impl/UserServiceImpl.java), [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/impl/ItemServiceImpl.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/impl/ItemServiceImpl.java), [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/impl/RequestServiceImpl.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/service/impl/RequestServiceImpl.java)

Exceptions:

- [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/exception/UserNotFoundException.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/exception/UserNotFoundException.java)
- [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/exception/ItemNotFoundException.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/exception/ItemNotFoundException.java)
- [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/exception/RequestNotFoundException.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/exception/RequestNotFoundException.java)

## Example payloads

Create user:

```json
{
   "username": "alex",
   "email": "alex@example.com",
   "password": "pass1234",
   "role": "USER"
}
```

Create item:

```json
{
   "name": "Black Wallet",
   "description": "Leather wallet with ID",
   "itemStatus": "LOST",
   "location": "Main lobby",
   "userId": "U-..."
}
```

Create request:

```json
{
   "message": "I can verify ownership",
   "itemId": "I-...",
   "userId": "U-..."
}
```

Update request status:

```json
{
   "status": "APPROVED",
   "reviewerId": "U-..."
}
```

## Run locally

1. Configure database and app settings in [src/main/resources/application.properties](src/main/resources/application.properties).
2. Run with Maven:

```bash
mvn spring-boot:run
```

## Build

```bash
mvn clean package
```

## Tests

- Entry test: [src/test/java/lk/ijse/cmjd109/lostAndFoundSystem/LostAndFoundSystemApplicationTests.java](src/test/java/lk/ijse/cmjd109/lostAndFoundSystem/LostAndFoundSystemApplicationTests.java)

## Utilities and scripts

- Maven wrapper: [mvnw](mvnw), [mvnw.cmd](mvnw.cmd)
- SQL helpers: [check_users.sql](check_users.sql), [fix_roles.sql](fix_roles.sql)

## Notes

- [src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/AssetRecoveryManagementApp.java](src/main/java/lk/ijse/cmjd109/lostAndFoundSystem/AssetRecoveryManagementApp.java) is currently empty.
