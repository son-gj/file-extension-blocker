# 1. 빌드 스테이지 (JDK 21 사용)
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# 2. 소스 코드 복사 및 빌드
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 3. 실행 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 4. 빌드 스테이지에서 생성된 jar 파일만 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 5. 포트 설정 및 실행
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]