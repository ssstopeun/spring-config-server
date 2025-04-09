# spring-cloud-config

- Ref : https://docs.spring.io/spring-cloud-config/docs/current/reference/html/

## 1. 개요

> Spring Cloud Config는 어플리케이션의 설정 값을 중앙에서 관리하고 여러 서비스에서 공유할 수 있도록 도와주는 구성 관리 도구입니다. 설정을 손쉽게 변경하고 새롭게 서버를 배포하지 않아도 반영이 된다는 장점이 있습니다.

## 2. 구성 요소

- Config Server : 중앙에서 설정 값을 관리하고 제공하는 서버
- Config Client : Config Server로 부터 설정 값을 가져와 사용하는 애플리케이션
- Backend Storage : 설정 값을 저장하는 저장소 (대표적으로 Git을 사용하지만 JDBC, Valut와 같은 다른 저장소도 활용할 수 있습니다.)

<image src = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FQC4Xs%2FbtrCS0QoktP%2FJTWsRVzvK4EBGTUbqlbu5K%2Fimg.png" width="700"/>

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
  spring.cloud.config.server.git.password=GitHub PAT
  ```
  
  username은 GitHub 계정 아이디,
  
  password는 **GitHub Personal Access Token(PAT)** 을 사용합니다.

  <br />

* GitHub PAT란?

  > PAT = Personal Access Token <br />
  > Github에서 비밀번호 대신 사용하는 인증 토믄으로 2021 이후 GitHub는 비밀번호 인증을 중단하고 PAT만 허용 

  
  * 주로 사용되는 곳:
  
    1. HTTPS 기반 Git clone/push

    2. Spring Cloud Config Server 인증

    3. GitHub API 호출

    4. GitHub Actions에서 private repo 접근


  * PAT 사용 시 주의사항
    1) 보안	노출 금지 (코드/커밋/환경변수 철저 관리)

    2) 유효기간	만료일 설정 가능 → 주기적 갱신 필요

    3) 생성 위치	GitHub → Settings > Developer settings > Personal access tokens

    4) 권한 최소화	필요한 범위만 체크 (예: repo, read:packages)

  * PAT 설정 방법

    1. 본인 프로필 사진 클릭하면 아래 이미지처럼 화면이 나옴

       <img src="resources/image-5.png" width="200">

    2. setting 버튼 누른 뒤, 사이드 바 맨 하단에 있는 developer setting 클릭

       <img src="resources/image-6.png" width="500">

    3. Personal Access Token -> Fine-grained tokens 클릭

       <img src="resources/image-7.png" width="500">

    4. Generate new token 버튼 클릭

       <img src="resources/image-8.png" width="500">
    
      ```text
        token name: token 이름
        token description: 토큰 설명(생략해도 됨)
        Resource owner: 토큰의 실소유자 설정
        Expiration: 토큰의 만료기간, No Expiration 설정 시 토큰 기한이 무제한
      ```  
      
      <img src="resources/image-9.png" width="500">
        
      ```text
        Repository access: Personal Access Token으로 접근할 수 있는 리포지토리를 설정

        -> config repository 접근하므로 All repository 
            또는 Only select repository 선택 (가급적 Only select repository 선택해서 config-repo 설정)
      ```
        
      <img src="resources/image-10.png" width="500">

      ```text
        Repository permissions 항목에서 반드시 Contents - Read access를 체크

        권한이 없으면 레포의 설정, 파일, 구성 정보를 읽을 수 없어 토큰이 정상 작동하지 않음

        특히 Spring Cloud Config Server에서 사용 시 꼭 필요함.

      ```


* SSH 인증 사용

  ```
  spring.cloud.config.server.git.uri=git@github.com:example/config-repo.git
  spring.cloud.config.server.git.privateKey=classpath:/config_server_deploy_key.rsa
  ```
  SSH 키는 PEM 형식의 RSA 키여야 합니다. (`-----BEGIN RSA PRIVATE KEY-----` 형식)

---
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
      server.port = 8080
      spring.cloud.config.uri=http://localhost:8888
      ```

---
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
  
  2) 깃 저장소의 설정값 변경 후 `/actuator/refresh` 호출
     ```
     curl -X POST http://localhost:8080/actuator/refresh
     ```

## 4. 실습
1. 현재 repo를 clone 받아 config-server, client, client2 를 실행합니다.
<img src="resources/image.png" width="500">  


2. http 폴더의 GET 명령어를 실행하여 봅니다.
<img src="resources/image-1.png" width="500"> 

    appName과 message는 client1, client2 Application의 properties에 없는 정보이지만 config-server를 통해 config 정보를 불러와 주입받게 됩니다.

    <img src="resources/image-2.png" width="500">


3. 개인 git repository에 config 설정파일을 저장한 후 이를 config-server와 직접 연결하여 봅니다. 그 후 설정을 바꾸어보고 refresh를 통해 반영이 되는지 확인해봅니다.
