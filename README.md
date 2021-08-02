 # MOGACO-BOT (1.0.11.RELEASE)
 
 디스코드에서 모각코 스터디를 운영하며 각자 공부한 시간 기록 및 랭크가 있으면 재밌을거같아 만들게 된 챗봇입니다.
 
 
 # 목차
 
 1. [사용 방법](#사용-방법)
 2. [사용 기술](#사용-기술)
 3. [기능 구현](#기능-구현)
 
    
  <br/>
  
 # 사용 방법
  
 디스코드 방에 봇을 초대합니다.
 
 초대 링크 : https://discord.com/oauth2/authorize?client_id=866016385042087967&scope=bot
 
 모각코봇은 **음성채널 참여을 기준**으로 사용자 참여율을 카운팅합니다.

 
 ### 명령어
 
 기본적으로 ! 를 선언한 뒤 명령어를 입력합니다.
 
 - !명령어 : 각 명령어에 대한 설명을 확인합니다.
 - !참여 : 디스코드 랭킹 시스템에 참여합니다.
 - !참여인원 : 현재 참여인원을 조회합니다.
 - !조회 : 금일 음성채널에 참여한 시간을 조회합니다.
 - !랭크 : 금주 참여 시간 랭크를 조회합니다.
 - !공지 : 공지를 조회합니다.
 - !공지 [공지] : 공지를 등록합니다.
 - !공지 삭제 : 공지를 삭제합니다.
 
 
    
 
  
 <br/>
 
 # 사용 기술
 
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
   
 - 배포 환경
   - AWS EC2, AWS RDS 사용
   - OS : Ubuntu
   - RDS : MySQL
   
   
   
  <br/>
  
  # 기능 구현
  
  ### 1. 디스코드 API
  
  - 디스코드 챗봇 라이브러리인 JDA를 사용하였습니다.   
  
  ```java
implementation "net.dv8tion:JDA:4.3.0_297"
  ```

  자세한 api 가이드는 이쪽을 참조해주시길 바랍니다. -  [JDA GIT Link](https://github.com/DV8FromTheWorld/JDA)
   
   
   <br/>
   
  ### 2. 명령어 처리
  
  명령어 관련하여 무한 분기 처리를 회피하기 위해 Reflection과 Custom Annotation를 만들어 사용했습니다.
  
  <br/>
  
 **CommandMapping**
  
  ```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandMapping {

    String command();

}
```
 
  <br/>
  
  
 **CommandMapping**
   
   ```java
 @Retention(RetentionPolicy.RUNTIME)
 @Target(ElementType.METHOD)
 public @interface CommandMapping {
 
     String command();
 
 }
 ``` 
   
   
**MessageController**   
   
```java
   @CommandMapping(command = "공지 삭제")
    public String deleteRole(){
        Config.setProperty("공지", "등록된 공지가 없습니다 :(");
        return "공지가 삭제되었습니다";
    }

```
 
 
**CommandHandler**
 
```java
    @PostConstruct
    public void loadMogacoMessage(){

        //@CommandMapping이 붙은 Method 탐색
        Reflections reflections =  new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.flat.mogaco")).setScanners(new MethodAnnotationsScanner()));

        Set<Method> methods = reflections.getMethodsAnnotatedWith(CommandMapping.class);

        try {
            methods.forEach(method -> {
                CommandMapping commandMappingAnnotation = method.getAnnotation(CommandMapping.class);
                String command = commandMappingAnnotation.command();
                String option = "None";
                if (command.contains(" ")) {
                    String[] arrayStr = command.split(" ");
                    command = arrayStr[0];
                    option = arrayStr[1];
                }
                Map<String, Method> optionMap = commandMethod.getOrDefault(command, new ConcurrentHashMap<>());
                log.debug("commandMethod option put :: {}, {}", command, optionMap);
                optionMap.put(option, method);
                commandMethod.put(command, optionMap);
            });
        }  catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }
```
 
 
 리플렉션을 활용하여 어노테이션을 추적해 메세지에 맞는 Controller 호출
 
 

