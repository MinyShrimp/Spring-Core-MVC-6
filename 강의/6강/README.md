# 스프링 MVC - 기본 기능
## 프로젝트 생성

## 로깅 간단히 알아보기
앞으로 로그를 사용할 것이기 때문에, 이번 시간에는 로그에 대해서 간단히 알아보자.

운영 시스템에는 `System.out.println`같은 시스템 콘솔을 사용해서 필요한 정보를 출력하지 않고,
별도의 로깅 라이브러리를 사용해서 로그를 출력한다.

참고로 로그 관련 라이브러리도 많고, 깊게 들어가면 끝이 없기 때문에, 여기서는 최소한의 사용 방법만 알아본다.

### 로깅 라이브러리
스프링 부트 라이브러리를 사용하면 스프링 부트 로깅 라이브러리(`spring-boot-starter-loggin`)가 함께 포함된다.
스프링 부트 로깅 라이브러리는 기본으로 다음 로깅 라이브러리를 사용한다.

* SLF4J, Logback

로그 라이브러리는 Logback, Log4J, Log4J2 등등 수 많은 라이브러리가 있는데, 그것을 통합해서 인터페이스로 제공하는 것이 바로 SLF4J 라이브러리다.

쉽게 이야기해서 SLF4J는 인터페이스이고, 그 구현체로 Logback 같은 로그 라이브러리를 선택하면 된다.
실무에서는 스프링부트가 기본적으로 제공하는 Logback을 대부분 사용한다.

### 로그 선언
* `private Logger log = LoggerFactory.getLogger(getClass())`
* `private static final Logger log = LoggerFactory.getLogger(Xxx.class)`
* `@Slf4j`: 롬복 사용 가능

### 로그 호출
* `log.info("hello")`
* `System.out.println("hello")`

시스템 콘솔로 직접 출력하는 것 보다 로그를 사용하면 다음과 같은 장점이 있다.
실무에서는 항상 로그를 사용해야 한다.

### LogTestController
```java
@RestController
public class LogTestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        // 로그를 사용하지 않아도 a + b 계산 로직이 먼저 실행됨, 이런 방식으로 사용하면 X
        // log.debug("String concat log = " + name);

        return "ok";
    }
}
```

### 매핑 정보
* `@RestController`
  * `@Controller`는 반환 값이 `String`이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 렌더링 된다.
  * `@RestController`는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다.
    따라서 실행 결과로 ok 메시지를 받을 수 있다. `@ResponseBody`와 관련이 있는데, 뒤에서 더 자세히 설명한다.

### 테스트
* 로그가 출력되는 포멧 확인
  * 시간, 로그 레벨, 프로세스 ID, 쓰레드 명, 클래스명, 로그 메시지
* 로그 레벨 설정을 변경해서 출력 결과를 보자
  * LEVEL: `TRACE > DEBUG > INFO > WARN > ERROR`
* `@Slf4j`로 변경

### 로그 레벨 설정
`application.properties`
```properties
# 전체 로그 레벨 설정 ( 기본 info )
logging.level.root=info

# hello.springmvc 패키지와 그 하위 로그 레벨 설정
logging.level.hello.springmvc=trace
```

### 올바른 로그 사용법
* `log.debug("data = " + data);`
  * 로그 출력 레벨을 info로 설정해도 해당 코드에 있는 `"data="+data` 가 실제 실행이 되어 버린다.
* `log.debug("data = {}, data);`
  * 로그 출력 레벨을 info로 설정하면 아무일도 발생하지 않는다.

### 로그 사용시 장점
* 쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.
* 로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영 서버에서는 출력하지 않는 등 로그를 상황에 맞게 조절할 수 있다.
* 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다.
  특히 파일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.
* 성늘도 일단 `System.out`보다 좋다. (내부 버퍼링, 멀티 쓰레드 등등)
  그래서 실무에서는 꼭 로그를 사용해야 한다.

### 더 공부하실 분
* 로그에 대해서 더 자세한 내용은 slf4j, logback을 검색해보자.
  * SLF4J: http://www.slf4j.org/
  * Logback: http://logback.qos.ch/
* 스프링 부트가 제공하는 로그 기능은 다음을 참고하자
  * https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.logging

## 요청 매핑

## 요청 매핑 - API 예시

## HTTP 요청 - 기본, 헤더 조회

## HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form

## HTTP 요청 파라미터 - @RequestParam

## HTTP 요청 파라미터 - @ModelAttribute

## HTTP 요청 메시지 - 단순 텍스트

## HTTP 요청 메시지 - JSON

## 응답 - 정적 리소스, 뷰 템플릿

## HTTP 응답 - HTTP API, 메시지 바디에 직접 입력

## HTTP 메시지 컨버터

## 요청 매핑 헨들러 어뎁터 구조

## 정리
