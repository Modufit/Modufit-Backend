# 멀티 스테이지 빌드: 빌드 단계
FROM gradle:7.6-jdk17 AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 파일 먼저 복사 (의존성 캐싱을 위해)
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# 의존성 다운로드 (캐시 활용)
RUN gradle dependencies --no-daemon || true

# 소스 코드 복사
COPY src ./src

# 애플리케이션 빌드 (테스트 제외)
RUN gradle clean bootJar -x test --no-daemon

# 실행 단계: 경량화된 JRE 이미지 사용
FROM eclipse-temurin:17-jre-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일 복사
# build/libs에서 생성된 JAR 파일을 app.jar로 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 애플리케이션 실행을 위한 포트 노출
EXPOSE 8080

# JVM 옵션 설정 (메모리 최적화)
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]