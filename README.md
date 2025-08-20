📌 Todo REST API

간단한 할 일 관리 REST API 입니다.
생성/조회/수정/삭제(CRUD)를 지원하며, 전역 예외 처리와 표준화된 에러 응답을 제공합니다.
향후 맛집 프로젝트 등으로 확장할 수 있도록 구조화되어 있습니다.

🚀 프로젝트 개요

이름: Todo REST API

설명: 할 일 관리 API (CRUD + 전역 예외 처리)

기술 스택:

Spring Boot

Spring Web

Spring Data JPA

H2 (개발용)

Gradle

✨ 주요 특징

REST API 기반 CRUD

전역 예외 처리 (ErrorResponse DTO)

통일된 JSON 오류 응답 포맷

Insomnia / Postman 테스트 용이

⚡ 빠른 시작
✅ 요구 사항

JDK 21

Gradle Wrapper (동봉: gradlew, gradlew.bat)

▶ 실행 방법
# Windows
.\gradlew clean bootRun

# macOS / Linux
./gradlew clean bootRun


기본 주소: http://localhost:8080

📚 API 요약
기능	메서드	URL	요청	응답
생성	POST	/todos?title={제목}	-	200/201 + Todo JSON
전체 조회	GET	/todos	-	[Todo, Todo, ...]
단건 조회	GET	/todos/{id}	-	Todo JSON
제목 수정	PUT	/todos/{id}/title?newTitle={제목}	-	수정된 Todo JSON
상태 변경	PUT	`/todos/{id}/status?done={true	false}`	-
삭제	DELETE	/todos/{id}	-	204 No Content 또는 메시지

⚠️ 파라미터 이름(done, newTitle)은 컨트롤러 선언과 동일해야 합니다.

❌ 오류 응답 포맷

전역 예외 처리(GlobalExceptionHandler)로 통일된 JSON 반환:

{
  "status": 404,
  "error": "Not Found",
  "message": "Todo not found with id 999",
  "path": "/todos/999",
  "timestamp": "2025-08-21T00:00:00"
}

⚙️ 설정 (application.yml)
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:testdb;MODE=MYSQL
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

📂 프로젝트 구조
src/main/java/com/example/todo
 ├── controller      # TodoController
 ├── service         # TodoService
 ├── repository      # TodoRepository
 ├── domain/entity   # Todo
 ├── dto             # ErrorResponse 등
 └── exception       # GlobalExceptionHandler

🧪 로컬 테스트 (Insomnia/Postman)
✅ 예시 요청
POST   http://localhost:8080/todos?title=첫번째
GET    http://localhost:8080/todos
GET    http://localhost:8080/todos/1
PUT    http://localhost:8080/todos/1/title?newTitle=제목수정
PUT    http://localhost:8080/todos/1/status?done=true
DELETE http://localhost:8080/todos/1

❌ 에러 테스트
GET http://localhost:8080/todos/99999
# → 404 ErrorResponse 반환

🏗 빌드 & 테스트
# 빌드
./gradlew clean build

# 테스트
./gradlew test

🔮 향후 계획

요청/응답 DTO 전환 (@RequestBody + @Valid)

페이지네이션 & 정렬 지원 (GET /todos?page=0&size=10&sort=id,desc)

Swagger (OpenAPI) 문서화

MariaDB 전환 (Docker) 및 dev/prod 프로필 분리

간단한 프론트엔드(HTML/CSS/JS) 연동

📜 라이선스

필요 시 라이선스 명시 (예: MIT)
