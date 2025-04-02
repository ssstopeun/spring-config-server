# spring-cloud-config

- Ref : https://docs.spring.io/spring-cloud-config/docs/current/reference/html/

## 1. 개요

> Spring Cloud Config는 어플리케이션의 설정 값을 중앙에서 관리하고 여러 서비스에서 공유할 수 있도록 도와주는 구성 관리 도구입니다. 설정을 손쉽게 변경하고 새롭게 서버를 배포하지 않아도 반영이 된다는 장점이 있습니다.

## 2. 구성 요소

- Config Server : 중앙에서 설정 값을 관리하고 제공하는 서버
- Config Client : Config Server로 부터 설정 값을 가져와 사용하는 애플리케이션
- Backend Storage : 설정 값을 저장하는 저장소 (대표적으로 Git을 사용하지만 JDBC, Valut와 같은 다른 저장소도 활용할 수 있습니다.)

<image src = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FQC4Xs%2FbtrCS0QoktP%2FJTWsRVzvK4EBGTUbqlbu5K%2Fimg.png" />

## 3. 사용 방법

### 1. Spring Cloud Coing Server 설정

1) 의존성 추가
   ```
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-config-server</artifactId>
   </dependency>
   ```

2) 어플리케이션 설정

   ```java
   @EnableConfigServer
   @SpringBootApplication
   public class ConfigServerApplication {
       public static void main(String[] args) {
           SpringApplication.run(ConfigServerApplication.class, args);
       }
   }
   ```

3) 설정 파일 (application.properties)

   ```
   server.port=8888
   spring.cloud.config.server.git.uri=https://github.com/example/config-repo
   spring.cloud.config.server.git.clone-on-start=true
   ```

    다음 설정은 public repository를 참조하는 예시로 private repository 참조 시 다음과 같이 인증정보를 추가하여야 합니다.

* HTTP Basic 인증 사용

  ```
  spring.cloud.config.server.git.uri=https://github.com/example/config-repo
  spring.cloud.config.server.git.username=my-username
  spring.cloud.config.server.git.password=my-password
  ```
* SSH 인증 사용

  ```
  spring.cloud.config.server.git.uri=git@github.com:example/config-repo.git
  spring.cloud.config.server.git.privateKey=classpath:/config_server_deploy_key.rsa
  ```
  SSH 키는 PEM 형식의 RSA 키여야 합니다. (`-----BEGIN RSA PRIVATE KEY-----` 형식)

### 2. Spring Cloud Config Client 설정
  1) 의존성 추가
      ```
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
      </dependency>
      ```

  2) 설정 파일 (application.properties)
      ```
      spring.application.name=my-service
      spring.cloud.config.uri=http://localhost:8888
      ```

### 3. 설정값 변경 후 반영 (Actuator Refresh)
변경된 설정값을 애플리케이션에 방영하려면 Spring Boot Actuator의 /actuator/refresh 엔드 포인트를 호출해야합니다.

  1) client 추가 설정
  - 의존성 추가
    ```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```

  - Actuator 활성화
    ```
    management.endpoints.web.exposure.include=refresh
    ```
  
  2) 설정값 변경후 `/actuator/refresh` 호출
    ```
    curl -X POST http://localhost:8080/actuator/refresh
    ```

## 4. Secure Key Manager



    

