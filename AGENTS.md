# Repository Guidelines

## Project Structure & Module Organization
This repository is a Maven-based Spring Boot 4 API targeting Java 21. Application code lives under `src/main/java/org/scopesky/jdktutorial`, with HTTP controllers in `controllers/`, business logic in `services/`, repositories in `repositories/`, DTOs in `dto/`, security code in `security/` and `config/`, and MapStruct mappers in `mapper/`. Runtime configuration is in `src/main/resources/application.yaml`. Tests belong in `src/test/java` using the same package layout as production code. Avoid editing `target/`; it is generated build output.

## Build, Test, and Development Commands
Use the Maven wrapper so contributors do not depend on a local Maven install.

- `./mvnw spring-boot:run` or `mvnw.cmd spring-boot:run`: start the API locally.
- `./mvnw test`: run the JUnit test suite.
- `./mvnw clean verify`: compile, run tests, and regenerate annotation-processed sources.
- `docker compose up -d`: start PostgreSQL on `localhost:5332`.

The app reads database settings from `src/main/resources/application.yaml`. Keep the Docker and application credentials aligned before running locally.

## Coding Style & Naming Conventions
Use 4-space indentation and standard Java naming: `PascalCase` for classes, `camelCase` for methods and fields, and uppercase snake case for constants. Keep controllers thin, push business rules into services, and expose DTOs instead of entities from API endpoints. Match package names to responsibility, for example `controllers/ProjectController.java` and `services/ProjectService.java`. Prefer constructor injection and keep mapper logic in MapStruct interfaces rather than hand-written conversions where possible.

## Testing Guidelines
Write JUnit-based tests under `src/test/java/org/scopesky/jdktutorial/...` with names ending in `Test`, such as `ProjectControllerTest`. Add tests for new controller endpoints, service behavior, and security-sensitive flows. Run `./mvnw test` before opening a PR. If a change affects persistence or mapping, include at least one test that exercises the changed path end to end.

## Commit & Pull Request Guidelines
Recent history uses short prefix-based subjects such as `FEAT:- Added Pagination` and `BUG:- Fixed include project names`. Keep that pattern, but make the message more specific when possible. Each PR should include a short summary, impacted endpoints or modules, any config changes, and request/response examples or screenshots for API-facing changes. Link the related issue when one exists.

## Configuration Notes
JWT settings and datasource credentials are stored in `application.yaml`. Do not commit real secrets. Prefer environment overrides for local or shared deployments.
