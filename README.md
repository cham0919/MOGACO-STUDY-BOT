 # MOGACO-BOT
 
 디스코드에서 모각코 스터디를 운영하며 각자 공부한 시간 기록 및 랭크가 있으면 재밌겠다싶어 만들게 된 챗봇입니다.
 
 
 <br/>
 
 # 시스템 구현
 
 - 사용 기술 
   - 언어 : Java jdk 1.8.0
   - Back : Spring Boot 2.4.2
   - ORM : Spring Data JPA, QueryDSL
   
 - 사용 도구
   
   - 소프트웨어 개발 도구 : IntelliJ
   - 데이터베이스: MySQL 8.0.23
   - WAS : Tomcat 9.0.41
   - 배포 도구 : Gradle
   - 형상관리 도구 : Github
   
   
   
  <br/>
  
  # 주요 기술 및 기능 구현
  
  ### 1. 디스코드 API
  
  - 디스코드 챗봇 라이브러리인 JDA를 사용하였습니다.   
  
  ```java
implementation "net.dv8tion:JDA:4.3.0_297"
  ```

  자세한 api 가이드는 이쪽을 참조해주시길 바랍니다. -  [JDA GIT Link](https://github.com/DV8FromTheWorld/JDA)
   
   
   <br/>
   
  ### 2. 명령어 처리
  
  명령어 관련하여 무한 분기 처리를 회피하기 위해 Reflection과 Custom Annotation를 만들어 사용했습니다.
  
  
  
  
  
  
  ```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandMapping {

    String command();

}
```
  
  <br/>
  
   
   
   
 
 
 
 